package team.antelope.fg.customized.trpay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Properties;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.db.dao.IUserDao;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

public class PayResult extends BaseActivity {

    private Properties mProp;
    private Long user_id;   //当前登录用户id
    private String uid_s;
    private String skillid;
    private String content;
    private String img;
    private String skilltype;
    private String skilltitle;

    @Override
    protected void initView(Bundle savedInstanceState) {

        Intent intent = getIntent();
        uid_s = intent.getStringExtra("uid_s");
        skillid = intent.getStringExtra("skillid");
        content = intent.getStringExtra("content");
        img = intent.getStringExtra("img");
        skilltype = intent.getStringExtra("skilltype");
        skilltitle = intent.getStringExtra("title");

        /**
         * @说明 获取当前登录用户的id
         * @创建日期 2018/5/18 下午8:11
         */
        IUserDao userDao = new UserDaoImpl(PayResult.this);
        User user = userDao.queryAllUser().get(0);
        user_id = user.getId();     //当前登录用户id

        mProp = PropertiesUtil.getInstance();
        sendRequestWithOkHttp();

    }

    @Override
    public int getLayout() {
        return R.layout.lx_payresult;
    }

    /**
     * @说明 建立连接
     * @创建日期 2018/5/20 下午10:43
     */
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = null;
                try {
                    url = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH) +
                            mProp.getProperty(AccessNetConst.ADDORDERENDPATH);
//                                    OkHttpClient client = new OkHttpClient();
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();
                    //POST方式
                    RequestBody requestBody = new FormBody.Builder()
                            .add("uid", String.valueOf(user_id))
                            .add("uid_s", uid_s)
                            .add("skillid", skillid)
                            .add("title", skilltitle)
                            .add("content", content)
                            .add("img", img)
                            .add("skilltype", skilltype)
                            .add("ispay", "1")
                            .add("isdelete", "0")
                            .add("iscomment", "0")
                            .build();
                    Log.i("trpay111", "1111");
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void showResponse(String responseData) {
                if (responseData != null) {
                    Log.i("trpay111", "成功"+"userid:"+user_id+"skillid:"+skillid);
                } else
                    Log.i("trpay111", "失败");
            }
        }).start();
    }//sendRequest
}
