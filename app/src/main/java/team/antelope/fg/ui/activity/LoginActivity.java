package team.antelope.fg.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.AppConst;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.dialog.CustomProgressDialog;
import team.antelope.fg.ui.presenter.impl.LoginPresenterImpl;
import team.antelope.fg.ui.view.ILoginView;
import team.antelope.fg.util.BlurBitmapUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.NetUtil;
import team.antelope.fg.util.SpUtil;
import team.antelope.fg.util.ToastUtil;

/**
 * @Author：hwc
 * @Date：2017/12/13 09:13
 * @Desc: 登入页面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        ILoginView {
    private LoginPresenterImpl mPresenter;/*view 的代理*/
    //控件
    private EditText et_username_login, et_password_login;
    private TextView tv_forget_password, tv_register;
    private Button btn_login_login;
    private Dialog dialog;
    private User user;

    private FgApp fgApp;
    //常量
    public static final int REQUEST_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fgApp = FgApp.getInstance();
        fgApp.addActivity(this);  //将此activity压入栈中管理
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏显示
        setContentView(R.layout.activity_login);
        initPresenter();/*初始化presenter*/
        initView();/*初始化view*/
        initListener();/*绑定监听事件*/
        initData();
    }

    private void initData() {
        User user = mPresenter.initData();
        if(user != null){
            et_username_login.setText(user.getName());
            et_password_login.setText(user.getPassword());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FgApp.getInstance().AppExit(this, false);
    }

    private void initView() {
        blurBgImg();
        et_username_login = (EditText) findViewById(R.id.et_username_login);
        et_password_login = (EditText) findViewById(R.id.et_password_login);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_pwd_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
    }

    /*初始化presenter*/
    protected void initPresenter(){
        mPresenter = new LoginPresenterImpl(this);
    }
    /*为控件绑定事件*/
    protected void initListener(){
        btn_login_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }
    /**
     * @Description 模糊背景图片
     * @date 2017/12/13
     */
    private void  blurBgImg  () {
        //拿到初始图
        Bitmap initBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
        //处理得到模糊效果的图
        Bitmap blurBitmap = BlurBitmapUtil.blurBitmap(this, initBitmap, 20f);
        Drawable basicImage =   new BitmapDrawable(getResources(), blurBitmap);
        View root = findViewById(R.id.rl_root_login);
        root.setBackground(basicImage);
    }

    @Override
    public void onClick(View view) {
        boolean isConnected = NetUtil.isConnected(this);
        if(!isConnected){
            showToast("请打开网络连接");
            return;
        }
        switch (view.getId()){
            case R.id.btn_login_login://点击登入按钮
                String username = et_username_login.getText().toString().trim();
                String password = et_password_login.getText().toString().trim();
                user = new User(username, password);
                User localUser = mPresenter.initData();
                if(localUser != null && localUser.getName().equals(username) &&
                        localUser.getPassword().equals(password)){
                    ToastUtil.showShort(getApplicationContext(), "登入成功");
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    SpUtil spUtil = fgApp.getSpUtil();
                    spUtil.setSP(getApplicationContext(), SpUtil.KEY_LOGINSTATE, AppConst.LOGIN_STATE);
                    break;
                }
                mPresenter.doLogin(user);
                showDialog(AccessNetConst.LOGINING);
                break;
            case R.id.tv_forget_pwd_login://点击忘记密码按钮
                showToast("此功能暂未实现");
                break;
            case R.id.tv_register://点击注册按钮
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default: break;
        }
    }
    //ILoginView  start
    @Override
    public void showToast(String msg) {
        ToastUtil.showCustom(getApplicationContext(), msg, 2000);
    }

    @Override
    public void showDialog(String msg) {
        dialog = CustomProgressDialog.createLoadingDialog(this, msg);
        dialog.show();
    }

    @Override
    public void closeDialog() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void asychStartActivity() {
        //跳转前保存name, pwd到偏好设置和数据库(如果没有)
        L.i("login", "asychStartActivity1");
        //将用户信息保存到数据库之前要查找该用户的id

        saveData();
        //跳转
        L.i("login", "asychStartActivity1");
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", user.getName());
        bundle.putString("password", user.getPassword());
        bundle.putString("email", user.getEmail());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    /**
     * @Description 保存name，pwd到本地
     * @date 2017/12/14
     */
    private void saveData() {
        mPresenter.saveData(user);
        L.i("whouser: ", user.toString());
    }
    //ILoginView  end


    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
        FgApp.getInstance().finishActivity(this);//将此activity从栈中移除
    }

    /**
     *
     * @date 2017/12/6
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
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
    /**
     * @Description 跳转回调方法
     * @date 2017/12/20
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RegisterActivity.RESULT_CODE){
            Bundle bundle = data.getExtras();
            String name = bundle.getString("username");
            String pwd= bundle.getString("password");
            et_username_login.setText(name);
            et_password_login.setText(pwd);
        }
    }
}
