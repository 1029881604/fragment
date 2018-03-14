package team.antelope.fg.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import team.antelope.fg.R;

/**
 * @Author：hwc
 * @Date：2017/12/5 17:14
 * @Desc: Toast的工具类，为了避免短时间内重复弹出吐司
 * 设置成相当于单例
 */

public class ToastUtil {

    private static Toast toast;
    private static Toast customToast;
    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 通过资源显示Toast
     * @param context 上下文
     * @param resId 要显示的资源id
     */
    public static void showShort(Context context, int resId) {
        showShort(context, (String) context.getResources().getText(resId));
    }
    /**
     * 长时间时间显示Toast
     */
    public static void showLong(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 通过资源长时间显示Toast
     * @param context 上下文
     * @param resId 要显示的资源id
     */
    public static void showLong(Context context, int resId) {
        showLong(context, (String) context.getResources().getText(resId));
    }
    /**
     * 自定义时间显示Toast
     */
    public static void show (Context context, String content, int time) {
        if (toast == null) {
            toast = Toast.makeText(context, content, time);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 通过资源自定义时间显示Toast
     * @param context 上下文
     * @param resId 要显示的资源id
     */
    public static void show(Context context, int resId, int time) {
        show(context, (String) context.getResources().getText(resId), time);
    }

    /**
     * 通过资源自定义风格显示Toast
     * @param context 上下文
     * @param resId 要显示的资源id
     */
    public static void showCustom(Context context, int resId, int time) {
        showCustom(context, (String) context.getResources().getText(resId), time);
    }
    /**
     * 自定义风格显示Toast
     */
    public static void showCustom (Context context, String content, int time) {
        View layout = LayoutInflater.from(context)
                .inflate(R.layout.common_toast_custom, null);
        TextView tv = layout.findViewById(R.id.tv_content_toast);
        tv.setText(content);
        if (customToast == null) {
            customToast = new Toast(context);
            customToast.setView(layout);
        } else {
            customToast.setView(layout);
        }
        customToast.show();
    }


}
