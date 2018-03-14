package team.antelope.fg.ui.model;

import team.antelope.fg.ui.model.callback.IOnRegisterCallback;
import team.antelope.fg.ui.model.callback.IOnSendVerificationCallBack;

/**
 * @Author：hwc
 * @Date：2017/12/13 21:16
 * @Desc: ...
 */

public interface IRegisterData<T> {
    void getServerData(final String url, IOnRegisterCallback<String> callback);
    T getLocalData();
    void doRegister(final String url, IOnRegisterCallback<String> callback);
    void setLocalData(T t);
    void doSendVerification(String url, IOnSendVerificationCallBack callback);

}
