package team.antelope.fg.ui.business;

import com.luck.picture.lib.tools.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import team.antelope.fg.FgApp;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;

/**
 * @Author：hwc
 * @Date：2018/1/1 20:55
 * @Desc: ...
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 1;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 4;
    private Retrofit mRetrofit;
    private static PropertiesUtil prop;
    private static RetrofitServiceManager instance;
    static {
        prop = PropertiesUtil.getInstance();
    }
    private RetrofitServiceManager(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //添加反馈时的网络拦截器
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Response originalResponse = chain.proceed(originalRequest);
                        //因为Response.body().string()只能调用一次， 所以不能在这里用完
//                      if (originalResponse.code() == 500 || originalResponse.body().string().equals("no login")) {//这里是由于我和后端司机没沟通好，有的端口未登录时请求的是500错误，有的是"no login"，按自己实际情况来；
                        if (originalResponse.code() == 401) {//这里和后端协调，有的端口未登录时请求的是500错误，有的是"no login"，按自己实际情况来；
                            L.i("intercept", "401exe");
                            originalResponse.body().close();
                            Request loginRequest = getLoginRequest();
                            Response loginResponse = chain.proceed(loginRequest);
                            if (loginResponse.isSuccessful()) {
                                L.i("intercept", "重新登入成功");
                                loginResponse.body().close();
                                return chain.proceed(originalRequest);
                            }
                        }
                        return originalResponse;
                    }

                    private Request getLoginRequest() {
                        String username = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_USERNAME, "");
                        String password = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_PASSWORD, "");
                        String url = prop.getProperty(AccessNetConst.BASEPATH) + prop.getProperty(AccessNetConst.LOGINENDPATH);
                        return new Request.Builder()
                                .url(url)//login路径
                                .post(new FormBody.Builder()
                                        .add("account", username)   //后端是按这个key来接收参数
                                        .add("password", password) //后端是按这个key来接收参数
                                        .build())
                                .build();
                    }
                })
                //添加cookie管理
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
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
                }); //设置cookie管理

        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间
        // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);
        // 创建Retrofit
        L.i("tag", "RetrofitServiceManager:RetrofitServiceManager--mid");
        Retrofit.Builder builder1 = new Retrofit.Builder();
        L.i("tag", "RetrofitServiceManager:R1");
        builder1.client(builder.build());
        L.i("tag", "RetrofitServiceManager:R2");
        builder1.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        L.i("tag", "RetrofitServiceManager:R3");
        builder1.addConverterFactory(GsonConverterFactory.create());
        L.i("tag", "RetrofitServiceManager:R4");
        String baseUrl = prop.getProperty(AccessNetConst.BASEPATH);
        L.i("tag", baseUrl);
        builder1.baseUrl(baseUrl);
        L.i("tag", "RetrofitServiceManager:R5");
        mRetrofit = builder1.build();
        L.i("tag", "RetrofitServiceManager:RetrofitServiceManager--end");
    }
    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        L.i("tag", "RetrofitServiceManager:getInstance1");
        if(instance == null){
            L.i("tag", "RetrofitServiceManager:getInstance2");
            synchronized (RetrofitServiceManager.class){
                if (instance == null){
                    L.i("tag", "RetrofitServiceManager:getInstance3");
                    instance = new RetrofitServiceManager();
                }
            }
        }
        L.i("tag", "RetrofitServiceManager:getInstance1 end");
        return instance;
    }
    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }

}
