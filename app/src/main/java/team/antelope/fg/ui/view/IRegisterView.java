package team.antelope.fg.ui.view;

import android.app.Dialog;

/**
 * @Author：hwc
 * @Date：2017/12/13 21:13
 * @Desc: 注册调用视图
 */

public interface IRegisterView {
    void showToast(String str);//注册页面只要显示是否注册成功和异步跳转
    void showProgressDialog(String str);  //显示进度
    void hideProgressDialog();
    void forbidClick(); // 禁止点击获取验证码
    void restore();     //还原点击或期货
    void asyncStartActivity();
    void setPresenter();
}
