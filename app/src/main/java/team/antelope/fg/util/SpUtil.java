package team.antelope.fg.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import static android.R.id.edit;

/**
 * @Author：hwc
 * @Date：2017/12/5 15:50
 * @Desc: SharePreference的工具类
 */

public class SpUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";
    /**
     * key
     */
    public static String KEY_USERNAME = "key_username";
    public static String KEY_PASSWORD = "key_password";
    public static String KEY_LOGINSTATE = "key_loginstate";
    public static String KEY_IS_CREATEDB = "key_is_createdb";
    public static String KEY_COOKIE = "Set-Cookie";

    private static SharedPreferences mSp;
    private static SharedPreferences.Editor mEditor;
    public SpUtil(Context context, String name) {
        mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }
    /**
     * @Description 获取key所对应的值
     * @date 2017/12/5
     */
    public static Object getSp(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        String packageName = context.getPackageName();
        if ("String".equals(type)) {
            return mSp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return mSp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return mSp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return mSp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return mSp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * @Description 设置key所对应的值
     * @date 2017/12/5
     */
    public static void setSP(Context context, String key, Object object) {
        String type = object.getClass().getSimpleName();
        String packageName = context.getPackageName();
        if ("String".equals(type)) {
            mEditor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            mEditor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            mEditor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            mEditor.putLong(key, (Long) object);
        }
        mEditor.apply();
    }

    public static void cleanAllSP(Context context) {
        mEditor.clear();
        mEditor.apply();
    }
    /**
     * @Description 查询某个key是否已经存在
     * @date 2017/12/5
     */
    public static boolean contains(Context context, String key)
    {
        return mSp.contains(key);
    }
    /**
     * @Description 移除某个key值已经对应的值
     * @date 2017/12/5
     */
    public static void remove(Context context, String key)
    {
        mEditor.remove(key);
        mEditor.apply();
    }

}
