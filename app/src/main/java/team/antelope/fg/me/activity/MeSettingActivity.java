package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AppConst;
import team.antelope.fg.ui.activity.LoginActivity;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.SpUtil;

public class MeSettingActivity extends BaseActivity implements View.OnClickListener{

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
                SpUtil spUtil = mApp.getSpUtil();
                spUtil.setSP(getApplicationContext(), SpUtil.KEY_LOGINSTATE, AppConst.UNLOGIN_STATE);
                SpUtil.remove(MeSettingActivity.this, SpUtil.KEY_COOKIE);
                Intent intent = new Intent(MeSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default: break;
        }
    }
}
