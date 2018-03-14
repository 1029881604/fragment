package team.antelope.fg.ui.model;

import android.content.Context;

import java.util.Properties;

import team.antelope.fg.entity.User;
import team.antelope.fg.ui.model.callback.IOnLoginCallback;

/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO ILoginModel 登录model接口，执行相应业务数据获取
 *
 */
public interface ILoginModel<T> {
	void getServerData(final String url, IOnLoginCallback<String> callback);
	User getLocalData(Context context);
	void saveData(T t, Context context);	//保存数据到本地
	void doLogin(final String url, IOnLoginCallback<String> callback);
}
