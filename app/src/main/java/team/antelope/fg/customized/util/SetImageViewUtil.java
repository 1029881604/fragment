package team.antelope.fg.customized.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Kyrene on 2018/4/9.
 */

/**
* @说明 设置网络图片到imageView控件中
* @创建日期 2018/4/9 上午11:17
*/
public class SetImageViewUtil {

    public static void setImageToImageView(final ImageView imageView , final String imgURL){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("HAHAHA", "设置图片成功");
                super.handleMessage(msg);
                Bitmap bitmap = (Bitmap)msg.obj;
                imageView.setImageBitmap(bitmap);
            }
        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.getBitmap(imgURL);//这是我封装的获取Bitmap的工具
//                bitmap.setHeight(245);//不能在这里设置bitmap相关参数
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }).start();
    }



}
