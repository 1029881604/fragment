package team.antelope.fg.ui.presenter.impl;

import android.app.Application;
import android.os.Handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import team.antelope.fg.FgApp;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.AppConst;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.model.ILoginModel;
import team.antelope.fg.ui.model.impl.LoginModelImpl;
import team.antelope.fg.ui.model.callback.IOnLoginCallback;
import team.antelope.fg.ui.presenter.ILoginPresenter;
import team.antelope.fg.ui.view.ILoginView;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;

/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO LoginPresenterImpl   login功能的代理实现类
 *
 */
public class LoginPresenterImpl implements ILoginPresenter<User>, IOnLoginCallback<String> {
	private ILoginView mView;
	private ILoginModel mModel;
	private Properties mProp;
	private FgApp mApp;
	Handler handler = new Handler();
	public LoginPresenterImpl(ILoginView IView) {
		mView = IView;
		mModel = new LoginModelImpl();
		mProp = PropertiesUtil.getInstance();
		mApp = FgApp.getInstance();
	}

	/*ILoginPresenter*/
	@Override
	public User initData() {
		return mModel.getLocalData(mApp.getApplicationContext());
	}

	@Override
	public void saveData(User user) {
		mModel.saveData(user, mApp.getApplicationContext());
	}

	/*ILoginPresenter*/
	@Override
	public void doLogin(User user) {
		String url = null;
		try {
			url = mProp.getProperty(AccessNetConst.BASEPATH)
					+ mProp.getProperty(AccessNetConst.LOGINENDPATH)
					+ "?account=" + URLEncoder.encode(user.getName() , "utf-8")
					+ "&password=" + user.getPassword();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		mModel.doLogin(url, this);
	}

	/*IOnLoginCallback*/
	@Override
	public void onFail(final String msg) {
		System.out.println(msg);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mView.closeDialog();
				mView.showToast(msg);
			}
		}, 300);
	}

	/*IOnLoginCallback*/
	@Override
	public void onSuccess(final String t) {
		System.out.println(t);
		L.i("login", t);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mView.closeDialog();
				mView.showToast(t);
				mView.asychStartActivity();
			}
		}, 300);

		//保存登入状态
		SpUtil spUtil = mApp.getSpUtil();
		spUtil.setSP(mApp.getApplicationContext(), SpUtil.KEY_LOGINSTATE, AppConst.LOGIN_STATE);
	}

	/*ILoginPresenter*/
	@Override
	public void onDestroy() {
		mView = null;
	}
}
