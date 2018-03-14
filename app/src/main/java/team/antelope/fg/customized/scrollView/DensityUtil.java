package team.antelope.fg.customized.scrollView;

import android.content.Context;

/**
 * Created by Kyrene on 2018/1/5.
 */

/**
* @说明 分辨率转换工具类
* @创建日期 2018/1/5 下午11:14
*/

public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
