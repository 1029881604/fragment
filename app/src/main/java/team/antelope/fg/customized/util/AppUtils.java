package team.antelope.fg.customized.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Kyrene
* @说明 App工具类
* @创建日期 2018/5/15 下午4:47
*/
public class AppUtils {

    /**
    * @说明 判断某个App是否安装
    * @创建日期 2018/5/16 上午8:16
    */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        //取得所有的PackageInfo
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        //判断包名是否在系统包名列表中
        return pName.contains(packageName);
    }

    /**
    * @说明 根据包名启动对应的App
    * @创建日期 2018/5/16 上午8:17
    */
    public static void startApp(Context context, String packageName) {
        Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(LaunchIntent);
    }

    /**
    * @说明 安装App
    * @创建日期 2018/5/16 上午8:18
    */
    public static void installApp(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
