package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.AppConst;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.activity.LoginActivity;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.business.NearbyBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.ui.business.UserBusiness;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;

public class MeSettingActivity extends BaseActivity implements View.OnClickListener{
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    Toolbar mToolbar;
    Button btn_finish_all_activity;
    private Button btn_exit_login;
    private FgApp mApp;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mApp = FgApp.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_finish_all_activity =(Button) findViewById(R.id.btn_finish_all_activity);
        btn_exit_login =(Button) findViewById(R.id.btn_exit_login);
        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initEvent();
    }

    private void initEvent() {
        btn_exit_login.setOnClickListener(this);
        btn_finish_all_activity.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.me_setting_activity;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_finish_all_activity:
                finish();
                mApp.AppExit(this, false);
                break;
            case R.id.btn_exit_login:
                SpUtil.setSP(mApp, SpUtil.KEY_LOGINSTATE, AppConst.UNLOGIN_STATE);
                SpUtil.remove(MeSettingActivity.this, SpUtil.KEY_COOKIE);
                //服务器端退出登入
                String endUrl = PropertiesUtil.getInstance().
                        getProperty(AccessNetConst.LOGOUTENDPATH);
                L.e("logout", "endUrl："+ endUrl);
                Observable<String> observable = RetrofitServiceManager.getInstance()
                        .create(UserBusiness.class).logout(endUrl)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());

                L.i("TAG", "observable:" + observable);
                addSubscription(observable.subscribe(new Subscriber<String>() {
                    private String returnStr;
                    @Override
                    public void onCompleted() {
                        L.e("logout", "complete");
                        L.e("logout", "return:" + returnStr);
                    }
                    @Override
                    public void onError(Throwable e) {
                        L.e("logout", "onError");
                    }

                    @Override
                    public void onNext(String str) {
                        L.e("logout", "onNext");
                        returnStr = str;
                    }
                }));
                //销毁activity
                Intent intent = new Intent(MeSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default: break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unSubscribe();  //注销时取消订阅
            }
        }, 100);
    }

    /**
     * @Description 订阅
     * @date 2018/1/5
     */
    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
    /**
     * @Description 取消订阅
     * @date 2018/1/5
     */
    public void unSubscribe(){
        if (compositeSubscription.hasSubscriptions()) {
            if (!compositeSubscription.isUnsubscribed()) {
                compositeSubscription.unsubscribe();
                compositeSubscription.clear();
            }
        }
    }
}
