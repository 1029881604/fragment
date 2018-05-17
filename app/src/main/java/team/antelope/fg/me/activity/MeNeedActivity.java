package team.antelope.fg.me.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Properties;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PersonNeed;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.MeNeedAdapter;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：Carlos
 * @Date： 2018/5/16 11:44
 * @Description: 我的需求
 **/
public class MeNeedActivity extends BaseActivity {
    Toolbar mToolbar;
    private  long id;
    private Properties mProp;
    private List<PublishNeed> pnList;
    private List<PublishNeed> publishNeeds;
    private ListView listView;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            publishNeeds= (List<PublishNeed>) msg.obj;
            MeNeedAdapter meNeedAdapter =new MeNeedAdapter(MeNeedActivity.this,publishNeeds);
            listView.setAdapter(meNeedAdapter);
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        listView =(ListView) findViewById(R.id.listView);
        mToolbar.setTitle("我的需求");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        UserDaoImpl userDao = new UserDaoImpl(MeNeedActivity.this);
        User ur = userDao.queryAllUser().get(0);
        id = ur.getId();
        sendOkHttpRequest();
    }

    /**
     * @Author：Carlos
     * @Date:  2018/5/8 9:43
     * @Description: okHttp发送请求，建立连接
     **/
    private void sendOkHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(AccessNetConst.BASEPATH)
                            +mProp.getProperty(MeAccessNetConst.GetUserNeedServletEndPath);
                    Request request = new Request.Builder()
                            .url(path+"?id="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    message.obj = pnList;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        pnList = gson.fromJson(responseData, new TypeToken<List<PublishNeed>>() {
        }.getType());
        for (PublishNeed publishNeed : pnList) {

            Log.d("JSONActivity", "id is" + publishNeed.getNeedtype());
            Log.d("JSONActivity", "name is" + publishNeed.getContent());
            Log.d("JSONActivity", "version is" + publishNeed.getTitle());
        }

    }

    @Override
    public int getLayout() {
        return R.layout.me_need_activity;
    }
}
