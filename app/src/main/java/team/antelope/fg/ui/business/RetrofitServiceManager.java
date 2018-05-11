package team.antelope.fg.ui.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
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
import team.antelope.fg.util.NetUtil;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;

/**
 * @Author：hwc
 * @Date：2018/1/1 20:55
 * @Desc: ...
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 3;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 8;
    private Retrofit mRetrofit;
    private static PropertiesUtil prop;
    private static RetrofitServiceManager instance;
    static {
        prop = PropertiesUtil.getInstance();
    }
    private RetrofitServiceManager(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间
        OkHttpClient httpClient = builder.build();
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
        builder1.client(httpClient);
        builder1.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder1.addConverterFactory(GsonConverterFactory.create());
        String baseUrl = prop.getProperty(AccessNetConst.BASEPATH);
        builder1.baseUrl(baseUrl);
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
