package team.antelope.fg.util;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.FgApp;
import team.antelope.fg.constant.AccessNetConst;

/**
 * @Author：hwc
 * @Date：2018/5/9 11:26
 * @Desc: ...  okhttp的工具类
 * 应用拦截器
不需要担心中间过程的响应,如重定向和重试.
总是调用一次,即使HTTP响应是从缓存中获取.
观察应用程序的初衷. 不关心OkHttp注入的头信息如: If-None-Match.
允许短路而不调用 Chain.proceed(),即中止调用.
允许重试,使 Chain.proceed()调用多次.

网络拦截器*********************坑， Chain.proceed()只能调用一次
能够操作中间过程的响应,如重定向和重试.
当网络短路而返回缓存响应时不被调用.
只观察在网络上传输的数据.
携带请求来访问连接.
 */

public class OkHttpUtils {
    public static OkHttpClient.Builder createHttpClientBuild(){
        //cookie容器
        final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        //缓存控制
        final CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .build();
        //client.build
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //添加cookie管理
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        if(cookies == null){
                            L.e("emsg", "cookies is null");
                            cookies = new ArrayList<>();
                            String cookieStr = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_COOKIE, "");
                            Cookie cookie = new Cookie.Builder()
                                    .hostOnlyDomain(httpUrl.host())
                                    .name("JSESSIONID").value(cookieStr.substring(cookieStr.indexOf('=')+1))
                                    .build();
                            cookies.add(cookie);

                        }
                        return cookies;
                    }
                })//设置cookie管理
// 已经设置了cookie管理， 不用在这里设置cookie，如果要用到别的，可以使用
 .addInterceptor(new Interceptor() {  //应用拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String cacheControl = request.cacheControl().toString();
                        if (!NetUtil.isConnected(FgApp.getInstance())) {
                            L.i("intercepter", "没网络了");
                            L.i("intercepter", "req.headers:" + request.headers().toString());
                            request = request.newBuilder()
                                    .cacheControl(TextUtils.isEmpty(cacheControl)? CacheControl.FORCE_CACHE:CacheControl.FORCE_NETWORK)
                                    .build();
                        }
                        Response originalResponse = chain.proceed(request);
                        if (NetUtil.isConnected(FgApp.getInstance())){
                            //有网的时候连接服务器请求,缓存一天
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + 1*60)
                                    .removeHeader("Pragma")
                                    .build();
                        } else {
                            //网络断开时读取缓存
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28)
                                    .removeHeader("Pragma")
                                    .build();
                        }
                    }
                })

                //添加反馈时的应用拦截器signingInterceptor
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Response originalResponse = chain.proceed(originalRequest);
                        //因为Response.body().string()只能调用一次， 所以不能在这里用完
                        if (originalResponse.code() == 401) {//这里和后端协调，有的端口未登录时请求的是500错误，有的是"no login"，按自己实际情况来；
                            L.i("intercept", "401exe");
                            originalResponse.body().close();
                            Request loginRequest = getLoginRequest(chain);
                            Response loginResponse = chain.proceed(loginRequest); //执行登入
                            L.i("intercept", "**********过:"+loginResponse);
                            if (loginResponse.isSuccessful()) {
                                //重新登入后sp文件里面的cookie要修改
                                //服务器端设置返回cookie&***********************************************
                                String baseUrl = PropertiesUtil.getInstance().getProperty(AccessNetConst.BASEPATH);
                                L.i("intercept", "baseUrl:"+baseUrl);
                                String host = baseUrl.substring(baseUrl.indexOf(':')+3, baseUrl.lastIndexOf(':'));
                                L.i("intercept", "host:"+host);
                                List<Cookie> cookies = cookieStore.get(host);
                                SpUtil.setSP(FgApp.getInstance(), SpUtil.KEY_COOKIE, cookies.get(0).toString());
                                L.i("intercept", "cookies.get(0).value():"+cookies.get(0).value());
                                L.i("intercept", "cookies.get(0).tostring:"+cookies.get(0).toString());

                                L.i("intercept", "loginResponse.isSuccessful:"+true);
                                loginResponse.body().close();
                                return chain.proceed(originalRequest);
                            }
                        }
                        return originalResponse;
                    }

                    private Request getLoginRequest(Chain chain) {
                        Properties prop = PropertiesUtil.getInstance();
                        String username = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_USERNAME, "");
                        String password = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_PASSWORD, "");
                        String url = prop.getProperty(AccessNetConst.BASEPATH) + prop.getProperty(AccessNetConst.LOGINENDPATH);

                        return chain.request().newBuilder()
                                .url(url)//login路径， 注意这里要加上命名空间因为这里不是用rxjava + retrofit, 这里在url.properties已经加了
                                .post(new FormBody.Builder()
                                        .add("account", username)   //后端是按这个key来接收参数
                                        .add("password", password) //后端是按这个key来接收参数
                                        .build())
                                .build();
                    }
                })
                .cache(new Cache(new File(FgApp.getInstance().getCacheDir(), "http"), 30 * 1024 * 1024))
                ;
        return builder;
    }

}
//  使用缓存
//    //缓存文件夹
//    File cacheFile = new File(getExternalCacheDir().toString(),"cache");
//    //缓存大小为10M
//    int cacheSize = 10 * 1024 * 1024;
//    //创建缓存对象
//    final Cache cache = new Cache(cacheFile,cacheSize);
//
//    OkHttpClient client = new OkHttpClient.Builder()
//            .cache(cache)
//            .build();
//    //设置缓存时间为60秒
//    CacheControl cacheControl = new CacheControl.Builder()
//            .maxAge(60, TimeUnit.SECONDS)
//            .build();
//    Request request = new Request.Builder()
//            .url(URL)
//            .cacheControl(cacheControl)
//            .build();
//
//    public static final CacheControl FORCE_CACHE = new Builder()
//            .onlyIfCached()
//            .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)
//            .build();
//    Request request = new Request.Builder()
//            .url(URL)
//            .cacheControl(Cache.FORCE_CACHE)
//            .build();
