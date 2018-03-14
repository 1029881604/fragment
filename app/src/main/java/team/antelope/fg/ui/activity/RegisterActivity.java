package team.antelope.fg.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.dialog.CustomProgressDialog;
import team.antelope.fg.ui.presenter.IRegisterPresenter;
import team.antelope.fg.ui.presenter.impl.RegisterPresenterImpl;
import team.antelope.fg.ui.view.IRegisterView;
import team.antelope.fg.util.BlurBitmapUtil;
import team.antelope.fg.util.ToastUtil;

/**
 * @Author：hwc
 * @Date：2017/12/13 10:57
 * @Desc: 注册页面
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        IRegisterView{

    private IRegisterPresenter mPresenter;
    //Activity返回码
    public static final int RESULT_CODE = 101;
    private EditText et_name, et_password, et_email, et_verification;
    private Button btn_submit, btn_verification;
    private Dialog dialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what>=0){
                btn_verification.setText(getString(R.string.btn_verification_text_register)+msg.what+"s");
            } else {
                btn_verification.setText(getString(R.string.btn_verification_text_register));
                btn_verification.setBackgroundColor(getResources().getColor(R.color.bg_vercode1));
                btn_verification.setEnabled(true);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FgApp.getInstance().addActivity(this);  //将此activity压入栈中管理
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏显示
        setContentView(R.layout.activity_register);
        setPresenter();
        initView();
        setListener();
    }

    private void initView() {
        blurBgImg();
        et_name = (EditText) findViewById(R.id.et_name_register);
        et_password = (EditText) findViewById(R.id.et_password_register);
        et_email = (EditText) findViewById(R.id.et_email_register);
        btn_submit = (Button) findViewById(R.id.btn_submit_register);
        et_verification = (EditText) findViewById(R.id.et_verification_register);
        btn_verification = (Button) findViewById(R.id.btn_verification_register);
    }
    /**
     * @Description 模糊背景图片
     * @date 2017/12/13
     */
    private void  blurBgImg  () {
        //拿到初始图
        Bitmap initBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.register_bg);
        //处理得到模糊效果的图
        Bitmap blurBitmap = BlurBitmapUtil.blurBitmap(this, initBitmap, 20f);
        Drawable basicImage =   new BitmapDrawable(getResources(), blurBitmap);
        View root = findViewById(R.id.ll_root_register);
        root.setBackground(basicImage);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_submit_register:
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String veriCode = et_verification.getText().toString().trim();
                User user = new User(name, password, email);
                mPresenter.doRegister(user, veriCode);
                showProgressDialog(AccessNetConst.LOADING);
                break;
            case R.id.btn_verification_register:
                btn_verification.setEnabled(false);
                btn_verification.setBackgroundColor(getResources().getColor(R.color.bg_vercode2));
                btn_verification.setTextColor(getResources().getColor(R.color.text_vercode));
                String email2 =  et_email.getText().toString().trim();
                mPresenter.doSendVerification(email2);
                break;
            default: break;
        }
    }

    @Override
    public void forbidClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int start = 0;
                int end = 60;
                while ((end-start) != 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    end --;
                    handler.sendEmptyMessage(end);
                }
                handler.sendEmptyMessage(-1);
            }
        }).start();
    }
    /**
     * @Description 失败情况下调用
     * @date 2017/12/15
     */
    @Override
    public void restore() {
        btn_verification.setText(getString(R.string.btn_verification_text_register));
        btn_verification.setBackgroundColor(getResources().getColor(R.color.bg_vercode1));
        btn_verification.setEnabled(true);
    }

    private void setListener() {
        btn_submit.setOnClickListener(this);
        btn_verification.setOnClickListener(this);
    }

    // IRegisterView start
    @Override
    public void showToast(String str) {
        ToastUtil.showCustom(getApplicationContext(), str, 2000);
    }

    @Override
    public void showProgressDialog(String str) {
        dialog = CustomProgressDialog.createLoadingDialog(this, str);
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void asyncStartActivity() {
        Intent intent = getIntent();
        Bundle extras = new Bundle();
        String name = et_name.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        extras.putString("username", name);
        extras.putString("password", pwd);
        extras.putString("email", pwd);
        intent.putExtras(extras);
        setResult(RESULT_CODE, intent);
        finish();
    }
    @Override
    public void setPresenter() {
         this.mPresenter = new RegisterPresenterImpl(this);
    }
    // IRegisterView end


    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
        FgApp.getInstance().finishActivity(this);//将此activity从栈中移除
    }
}
