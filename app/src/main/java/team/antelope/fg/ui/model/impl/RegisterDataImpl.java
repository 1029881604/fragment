package team.antelope.fg.ui.model.impl;

import team.antelope.fg.ui.asynctask.RegisterAsyncTask;
import team.antelope.fg.ui.asynctask.ReqVeriCodeAsyncTask;
import team.antelope.fg.ui.model.IRegisterData;
import team.antelope.fg.ui.model.callback.IOnRegisterCallback;
import team.antelope.fg.ui.model.callback.IOnSendVerificationCallBack;

/**
 * @Author：hwc
 * @Date：2017/12/13 21:16
 * @Desc: 注册model接口实现
 */

public class RegisterDataImpl implements IRegisterData<String> {
    @Override
    public String getLocalData() {
        return null;
    }

    //从本地获取数据
    @Override
    public void setLocalData(String t) {

    }
    //注册
    @Override
    public void doRegister(String url, IOnRegisterCallback<String> callback) {
        getServerData(url, callback);
    }

    //同步服务器数据
    @Override
    public void getServerData(String url, IOnRegisterCallback<String> callback) {
        new RegisterAsyncTask(callback).execute(url);
    }

    @Override
    public void doSendVerification(String url, IOnSendVerificationCallBack callback) {
        new ReqVeriCodeAsyncTask(callback).execute(url);
    }


}
