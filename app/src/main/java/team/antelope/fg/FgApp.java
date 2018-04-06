package team.antelope.fg;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import team.antelope.fg.constant.AppConst;
import team.antelope.fg.db.DBUtil;
import team.antelope.fg.db.dao.impl.AttentionDaoImpl;
import team.antelope.fg.db.dao.impl.CompleteCustomDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.CompleteCustom;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;

/**
 * @Author：hwc
 * @Date：2017/12/5 15:23
 * @Desc: ...
 */

public class FgApp extends Application {
    private static FgApp app;   //当前对象单例
    private static Stack<Activity> activityStack;//activity栈，便于管理
    private static SpUtil mSpUtil;  //用application实例保存SharedPreferences
    private static PropertiesUtil mProp;
    public static Map<String, Double> tudes ;  //经纬度
    /**
     * @Description 修复java.lang.NoClassDefFoundError: team.antelope.fg.db.DBOpenHelper
     * @date 2017/12/26
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        L.i("aaa");
        app = this;
        this.activityStack = new Stack();
        mSpUtil = getSpUtil();
        initDB();
        mProp = PropertiesUtil.getInstance0(getResources());
        tudes = new HashMap<String, Double>();
    }
    /**
     * @Description 初始化数据库和创建表
     * @date 2017/12/13
     */
    private void initDB () {
        String isCreate = (String) mSpUtil.getSp(getApplicationContext(), SpUtil.KEY_IS_CREATEDB, AppConst.DBNOTCREATE);
        if(AppConst.DBNOTCREATE.equals(isCreate)){
            DBUtil.getInstance(this).createDB();
            mSpUtil.setSP(getApplicationContext(), SpUtil.KEY_IS_CREATEDB, AppConst.DBCREATE);
        }
    }
    /**
     * @Description 返回单例的应用对象
     * @date 2017/12/5
     */
    public static FgApp getInstance(){
        return app;
    }
    /**
     * @Description 获取SharedPreferences
     * @date 2017/12/5
     */
    public SpUtil getSpUtil() {
        if (mSpUtil == null) {
            mSpUtil = new SpUtil(this, SpUtil.FILE_NAME);
        }
        return mSpUtil;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        try {
            Activity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
}
