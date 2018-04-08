package team.antelope.fg.ui.asynctask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import android.os.AsyncTask;

import com.google.gson.Gson;

import team.antelope.fg.ui.model.callback.IOnLoginCallback;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO LoginAsyncTask 登入的异步任务类
 * 
 */
public class LoginAsyncTask extends AsyncTask<String, String, String> {

	IOnLoginCallback<String> callback;
	String path;
	private String responseContent;
	private PropertiesUtil mProp;
	public static final String REQUEST_FAIL="请求失败";
	public static final String LOGIN_SUCCESS="登入成功";
//	public static final String NEED_NAME="请输入用户名";
//	public static final String NEED_PWD="请输入密码";
//	public static final String ERROR_INPUT="账号或密码错误";
	
	public LoginAsyncTask(IOnLoginCallback<String> callback, String url) {
		this.callback = callback;
		this.path = url;
		this.mProp = PropertiesUtil.getInstance();
	}
	/**
	 * @Description 执行访问网络的耗时操作
	 * @date 2017/12/13
	 */
	@Override
	protected String doInBackground(String... params) {
		int a =params.length;
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("GET");
			if(mProp.getProperty("cookie") != null){
				conn.setRequestProperty("Cookie", mProp.getProperty("cookie"));
				System.out.println("cookie:"+mProp.getProperty("cookie"));
			} else{
				String cookieValuePair = conn.getHeaderField("Set-Cookie");
				if(cookieValuePair != null){
					System.out.println("conn:"+conn+ "cookievaluepair:"+cookieValuePair);
					mProp.setProperty("cookie", cookieValuePair.substring(0, cookieValuePair.indexOf(";")));
					System.out.println("cookie:"+mProp.getProperty("cookie"));
				}
			}
		
			/*Cannot set request property after connection is made*/
			int code = conn.getResponseCode();
			if(code == HttpURLConnection.HTTP_OK){
				InputStream is = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while((len = is.read(buffer)) != -1){
					baos.write(buffer, 0, len);
				}
				responseContent = new String(baos.toByteArray(), "utf-8");

				L.i("login", responseContent);

				return responseContent;
			} else{
				System.out.println("请求码不为200");
				return REQUEST_FAIL;
			}
			
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
	/**
	 * @Description 访问网络之后执行
	 * @date 2017/12/13
	 */
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		if(REQUEST_FAIL.equals(result)){
			callback.onFail(result);
			return;
		}
		String[] strings = gson.fromJson(result, String[].class);
		if(LOGIN_SUCCESS.equals(strings[0])){
			callback.onSuccess(result);
		}
	}
}
