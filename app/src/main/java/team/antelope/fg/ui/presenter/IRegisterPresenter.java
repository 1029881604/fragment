package team.antelope.fg.ui.presenter;

import team.antelope.fg.entity.User;

/**
 * @Author：hwc
 * @Date：2017/12/13 21:14
 * @Desc: 注册代理接口
 */

public interface IRegisterPresenter {
    void doRegister(User user, String veriCode);
    void onDestroy();
    void doSendVerification(String email);
}
