package team.antelope.fg.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import okhttp3.OkHttpClient;
import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.util.AppUtils;
import team.antelope.fg.util.L;
import team.antelope.fg.util.StatusBarUtil;

import static android.R.string.ok;

/**
 * @Author hwc
 * @Date 2017/12/6
 * @TODO BaseActivity
 * 
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();  //见名知意
        SetStatusBarColor();
        setContentView(getLayout());
        initView(savedInstanceState);//初始化视图
    }
    /**
     * @Description 初始化视图，子类实现
     * @date 2017/12/6
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * @Description 获取布局文件的id   抽象方法，从我这继承的子类实现
     * @date 2017/12/5
     */
    public abstract int getLayout();

    /**
     * @Description 在设置布局文件之前调用一些配置
     * @date 2017/12/5
     */
    private void doBeforeSetcontentView() {
        FgApp.getInstance().addActivity(this);  //将此activity压入栈中管理
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没有系统自带的标题栏
        if(AppUtils.getAppCurrentSdkVersion() >= 20){
            setTheme(R.style.HighVersionTheme);
            L.i("style", "doBeforeSetcontentView");
        } else {
            L.i("style", "doBeforeSetcontentView1"+getTheme().toString());
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏显示
    }
    /**
     * @Description 设置默认状态栏的颜色
     * @date 2017/12/5
     */
    protected void SetStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
    /**
     * @Description 通过给定的资源颜色设置状态栏的颜色
     * @date 2017/12/5
     * @param  color  资源文件id
     */
    protected void SetStatusBarColor(int color) {
        StatusBarUtil.setColor(this, color);
    }
    /**
     * @Description 设置透明状态栏
     * @date 2017/12/5
     */
    protected void SetTranslanteBar() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FgApp.getInstance().finishActivity(this);//将此activity从栈中移除
    }
    /**
     * @Description 用我重写的finish可以实现activity跳转动画
     * @date 2017/12/6
     */
    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }


    /**
     * @Description 用我重写的onBackPressed实现activity跳转动画
     * @date 2017/12/6
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * @Description 用我重写的startActivity实现activity跳转动画
     * @date 2017/12/6
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * @Description ...同上
     * @date 2017/12/6
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
