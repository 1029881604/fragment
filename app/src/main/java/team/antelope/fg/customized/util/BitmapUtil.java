package team.antelope.fg.customized.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kyrene on 2018/4/9.
 */

/**
* @说明 lx:封装的获取bitmap的工具类，通过url获取网上图片（android中这一操作需在异步线程中进行）
* @创建日期 2018/4/9 上午11:17
*/

public class BitmapUtil {
    
    public static Bitmap getBitmap(String url) {
        URL imageURL = null;
        Bitmap bitmap = null;
        Log.e("inuni","URL = "+url);
        try {
            imageURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageURL
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @说明 图片缩放(暂时没什么用,用了也没效果还乱报错不反馈,不如不用)
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }


}
