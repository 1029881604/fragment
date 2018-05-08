package team.antelope.fg.ui.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

import team.antelope.fg.FgApp;
import team.antelope.fg.ui.model.callback.IOnSendVerificationCallBack;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;
import team.antelope.fg.util.StreamUtil;

public class ReqVeriCodeAsyncTask extends AsyncTask<String, String, String> {
	private String respContent;
	private IOnSendVerificationCallBack mCallback;
	private PropertiesUtil mProp;
	public static final String NEED_EMAIL = "请输入邮箱";
	public static final String SEND_SUCCESS = "请去邮箱查看验证码";
	public static final String REQUEST_FAIL = "请求失败";

	public ReqVeriCodeAsyncTask(IOnSendVerificationCallBack callback) {
		mCallback = callback;
		this.mProp = PropertiesUtil.getInstance();
	}

	@Override
	protected String doInBackground(String... params) {
		String path = params[0];
		URL url = null;
		HttpURLConnection conn = null;

		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(60000);
			conn.setRequestMethod("GET");
			String cookieVal = (String) SpUtil.getSp(FgApp.getInstance(), SpUtil.KEY_COOKIE, "");
			if(cookieVal!= null  && !"".equals(cookieVal)){
				conn.setRequestProperty("Cookie", cookieVal);
				System.out.println("cookie:"+cookieVal);
			} else{
				//之前没有存储cookie， 则直接访问服务器去获取新的cookie
				String cookieValuePair = conn.getHeaderField("Set-Cookie");
				if(cookieValuePair != null){
					System.out.println("conn:"+conn+ "cookievaluepair:"+cookieValuePair);
					//存入properties
					mProp.setProperty("cookie", cookieValuePair.substring(0, cookieValuePair.indexOf(";")));
					System.out.println("cookie:"+mProp.getProperty("cookie"));
					//存入SharedPreferences
					SpUtil.setSP(FgApp.getInstance(), SpUtil.KEY_COOKIE, cookieValuePair);
				}
			}

			int code = conn.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				InputStream is = conn.getInputStream();
				respContent = StreamUtil.getString(is);
				return respContent;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.disconnect();
				conn = null;
			}
		}

		return REQUEST_FAIL;
	}

	@Override
	protected void onPostExecute(String result) {
		if(SEND_SUCCESS.equals(result)){
			mCallback.onSuccess(result);
		} else {
			mCallback.onFail(result);
		}
	}

}
