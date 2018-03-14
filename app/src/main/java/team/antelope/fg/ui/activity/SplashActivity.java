package team.antelope.fg.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AppConst;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.SpUtil;

/**
 * @Author hwc
 * @Date 2017/12/17
 * @TODO SplashActivity  初始化页面
 *
 */
public class SplashActivity extends AppCompatActivity {
    private FgApp mApp;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = FgApp.getInstance();
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FgApp.getInstance().finishActivity(this);//将此activity从栈中移除
    }
    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }
    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.into_scale_start, R.anim.into_scale_end);
        SpUtil spUtil = mApp.getSpUtil();
        String state = (String) spUtil.getSp(mApp.getApplicationContext(), SpUtil.KEY_LOGINSTATE, AppConst.UNLOGIN_STATE);
        if (AppConst.LOGIN_STATE.equals(state)) {
            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    forwardMain();
                }
            });
        } else {
            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    forwardLogin();
                }
            });
        }
    }
    /**
     * @Description 跳转登入
     * @date 2017/12/18
     */
    private void forwardLogin(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
    /**
     * @Description 已登入，跳转主函数
     * @date 2017/12/18
     */
    private void forwardMain(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(ForwardConst.FORM, ForwardConst.SPLASHACTIVITY);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
