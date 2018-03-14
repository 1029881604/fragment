package team.antelope.fg.ui.business;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：hwc
 * @Date：2018/1/1 20:55
 * @Desc: ...
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 2;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 8;
    private Retrofit mRetrofit;
    private static PropertiesUtil prop;
    private static RetrofitServiceManager instance;
    static {
        prop = PropertiesUtil.getInstance();
    }
    private RetrofitServiceManager(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
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
