package team.antelope.fg.util;

import android.util.Base64;

/**
 * @Author：hwc
 * @Date：2017/12/5 16:03
 * @Desc: 字符串编码解码的工具类
 */

public class Base64Util {
    /**
     * @Description 用Base64编码
     * @date 2017/12/5
     */
    public static String base64Encode(String str) {
        return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
    }

    /**
     * @Description 用Base64解码
     * @date 2017/12/5
     */
    public static String base64Decode(String str) {
        return Base64.decode(str.getBytes(), Base64.DEFAULT).toString();
    }
}
