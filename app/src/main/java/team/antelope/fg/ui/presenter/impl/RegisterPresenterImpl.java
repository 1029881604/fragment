package team.antelope.fg.ui.presenter.impl;

import android.os.Handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.model.IRegisterData;
import team.antelope.fg.ui.model.impl.RegisterDataImpl;
import team.antelope.fg.ui.model.callback.IOnRegisterCallback;
import team.antelope.fg.ui.model.callback.IOnSendVerificationCallBack;
import team.antelope.fg.ui.presenter.IRegisterPresenter;
import team.antelope.fg.ui.view.IRegisterView;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：hwc
 * @Date：2017/12/13 21:14
 * @Desc: ...
 */

public class RegisterPresenterImpl implements IRegisterPresenter {

    private IRegisterData<String> mData;
    private IRegisterView mView;

    public RegisterPresenterImpl(IRegisterView view) {
        mView = view;
        mData = new RegisterDataImpl();
    }

    @Override
    public void doRegister(User user, String veriCode) {
        System.out.println(user.toString());
        PropertiesUtil prop = PropertiesUtil.getInstance();
        String url = null;
        try {
            url = prop.getProperty(AccessNetConst.BASEPATH)
                    + prop.getProperty(AccessNetConst.REGISTERENDPATH)
                    + "?username=" + URLEncoder.encode(user.getName(), "utf-8")
                    + "&password=" + user.getPassword()
                    + "&email=" + user.getEmail()
                    + "&veriCode=" + veriCode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mData.doRegister(url, new IOnRegisterCallback<String>() {
            @Override
            public void onSuccess(String t) {
                mView.hideProgressDialog();
                mView.showToast(t);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        mView.asyncStartActivity();
                    }
                }, 1500);
            }
            @Override
            public void onFail(String msg) {
                mView.hideProgressDialog();
                mView.showToast(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void doSendVerification(String email) {
        PropertiesUtil props = PropertiesUtil.getInstance();
        String url = props.getProperty(AccessNetConst.BASEPATH)
                + props.getProperty(AccessNetConst.GETREQVERICODEENDPATH)
                + "?email=" + email;
        mData.doSendVerification(url, new IOnSendVerificationCallBack() {
            @Override
            public void onSuccess(String msg) {
                mView.showToast(msg);
                mView.forbidClick();    //60s后才能再次获取验证码
            }

            @Override
            public void onFail(String msg) {
                mView.showToast(msg);
                mView.restore();
            }
        });
    }

}
