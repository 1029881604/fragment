package team.antelope.fg.ui.model.impl;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.asynctask.LoginAsyncTask;
import team.antelope.fg.ui.model.ILoginModel;
import team.antelope.fg.ui.model.callback.IOnLoginCallback;
import team.antelope.fg.util.SpUtil;

/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO LoginModelImpl model 的实现类
 *
 */
public class LoginModelImpl implements ILoginModel<User> {

	@Override
	public void getServerData(final String url, IOnLoginCallback<String> callback) {
		new LoginAsyncTask(callback, url).execute();
	}

	@Override
	public User getLocalData(Context context) {
		SpUtil mSpUtil = new SpUtil(context, SpUtil.FILE_NAME);
		String username = (String) mSpUtil.getSp(context, SpUtil.KEY_USERNAME, "");
		String password = (String) mSpUtil.getSp(context, SpUtil.KEY_PASSWORD, "");
		if("".equals(username) || "".equals(password)){
            List<User> users = new UserDaoImpl(context).queryAllUser();
            if(users != null && !username.isEmpty()){
                User user = users.get(0);
                return  user;
            }
            return null;
		} else{
			List<User> users = new UserDaoImpl(context).queryAllUser();
			if(users != null && !username.isEmpty()){
				Iterator<User> iterator = users.iterator();
				while (iterator.hasNext()){
					User user = iterator.next();
					if(user.getName().equals(username) && user.getPassword().equals(password)){
						return user;
					}
				}
			}
		}
		return  null;
	}

	@Override
	public void saveData(User user, Context context) {
		SpUtil mSpUtil = new SpUtil(context, SpUtil.FILE_NAME);
		String username = user.getName();
		String password = user.getPassword();
		mSpUtil.setSP(context, SpUtil.KEY_USERNAME, username);
 		mSpUtil.setSP(context, SpUtil.KEY_PASSWORD, password);
		UserDaoImpl userDao = new UserDaoImpl(context);
		//删除所有user记录，添加当前用户
		userDao.deleteAll();
		userDao.insert(user);
	}

	public void doLogin(final String url, IOnLoginCallback<String> callback) {
		getServerData(url, callback);
	}



}
