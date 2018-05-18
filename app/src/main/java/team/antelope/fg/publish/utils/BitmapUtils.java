package team.antelope.fg.publish.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * @Author: uniquelry
 * @Date: 2018/5/18 14:34
 * @Email: 1909506001@qq.com
 * @Description: Bitmap相关工具类
 */
public class BitmapUtils {
    /**
     * 把图片转化为圆形
     * @param bitmap
     * @return
     */
    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        //这里可能需要调整一下图片的大小来让你的图片能在圆里面充分显示
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        //构建一个位图对象，画布绘制出来的图片将会绘制到此bitmap对象上
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        //构建一个画布,
        Canvas canvas = new Canvas(bm);
        //获得一个画笔对象，并设置为抗锯齿
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //获得一种渲染方式对象
        //BitmapShader的作用是使用一张位图作为纹理来对某一区域进行填充。
        //可以想象成在一块区域内铺瓷砖，只是这里的瓷砖是一张张位图而已。
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //设置画笔的渲染方式
        paint.setShader(shader);
        //通过画布的画圆方法将渲染后的图片绘制出来
        canvas.drawCircle(100, 100, 100, paint);
        //返回的就是一个圆形的bitmap对象
        return bm;
    }
}
