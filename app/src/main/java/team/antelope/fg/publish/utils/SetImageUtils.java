package team.antelope.fg.publish.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.util.OkHttpUtils;

/**
 * @Author: uniquelry
 * @Date: 2018/5/18 13:25
 * @Email: 1909506001@qq.com
 * @Description: 用于设置ImageView图片，加载网络图片
 */
public class SetImageUtils {
    private static final int SUCCESS = 1;
    private static final int FALL = 0;

    public static void setBitmap(final ImageView img, String url){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    //加载网络成功进行UI的更新,处理得到的图片资源
                    case SUCCESS:
                        //通过message，拿到字节数组
                        byte[] Picture = (byte[]) msg.obj;
                        //使用BitmapFactory工厂，把字节数组转化为bitmap
                        Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                        //通过imageview，设置图片
                        bitmap=BitmapUtils.makeRoundCorner(bitmap);
                        img.setImageBitmap(bitmap);
                        break;
                    //当加载网络失败执行的逻辑代码
                    case FALL:
                        break;
                }
            }
        };

        //1.创建一个okhttpclient对象
        OkHttpClient okHttpClient = OkHttpUtils.createHttpClientBuild().build();
        //2.创建Request.Builder对象，设置参数，请求方式如果是Get，就不用设置，默认就是Get
        Request request = new Request.Builder()
                .url(url)
                .build();
        //3.创建一个Call对象，参数是request对象，发送请求
        Call call = okHttpClient.newCall(request);
        //4.异步请求，请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到从网上获取资源，转换成我们想要的类型
                byte[] Picture_bt = response.body().bytes();
                //通过handler更新UI
                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = SUCCESS;
                handler.sendMessage(message);
            }
        });

    }
}
