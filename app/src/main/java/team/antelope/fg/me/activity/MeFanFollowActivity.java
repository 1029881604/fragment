package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import team.antelope.fg.entity.Person;
import team.antelope.fg.me.adapter.MeFanFollowAdapter;
import team.antelope.fg.me.adapter.MeFansListAdapter;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.CircleImageViewUtil;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：Carlos
 * @Date： 2018/5/16 11:42
 * @Description: 登录用户的粉丝的关注列表
 **/
public class MeFanFollowActivity extends BaseActivity {

    Toolbar mToolbar;
    private TextView fans_id;
    private TextView fans_name;
    private CircleImageViewUtil fans_head;
    private ListView listView;
    private  Long person_id;
    private List<Person> psList;
    private List<Person> personList;
    private Properties mProp;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            personList= (List<Person>) msg.obj;
            MeFanFollowAdapter meFanFollowAdapter = new MeFanFollowAdapter(MeFanFollowActivity.this,personList);
            listView.setAdapter(meFanFollowAdapter);

        }
    };
    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("关注列表");

        fans_name = (TextView) findViewById(R.id.fans_name);
        fans_head = (CircleImageViewUtil) findViewById(R.id.fans_head);
        listView = (ListView) findViewById(R.id.listView);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        if (String.valueOf(getIntent().getLongExtra("others_id",0l)).equals(0))
//        {
//            Log.i("8888888", String.valueOf(getIntent().getLongExtra("others_id",0l)));
//        }
//
//        User user = new UserDaoImpl(this).queryAllUser().get(0);
//        person_id = user.getId();
        person_id= getIntent().getLongExtra("userId",0l);
        sendOkHttpRequest();
        initListView();
        initEventView();
    }
    /**
     * @Author：Carlos
     * @Date:  2018/5/7 8:58
     * @Description: 初始化事件
     **/
    private void initEventView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fans_id = (TextView) findViewById(R.id.fans_id);
                Long fanId= Long.valueOf(fans_id.getText().toString());
                Intent intent = new Intent(MeFanFollowActivity.this,MePersonActivity.class);
                intent.putExtra("person_id",fanId);
                startActivity(intent);
            }
        });
    }

    /**
     * @Author：Carlos
     * @Date:  2018/4/25 8:22
     * @Description:  建立连接
     **/
    private void sendOkHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("person_id", String.valueOf(person_id))
                            .build();
                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(AccessNetConst.BASEPATH)
                            +mProp.getProperty(MeAccessNetConst.PostFindFansServletEndPath);
                    Request request = new Request.Builder()
                            .url(path)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    message.obj = psList;

                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @Author：Carlos
     * @Date:  2018/4/25 8:22
     * @Description:  解析Json
     **/
    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        psList = gson.fromJson(responseData, new TypeToken<List<Person>>() {
        }.getType());
        for (Person person : psList) {

            Log.d("JSONActivity", "id is" + person.getId());
            Log.d("JSONActivity", "name is" + person.getName());
            Log.d("JSONActivity", "version is" + person.getEmail());
        }

    }

    /**
     * @Author：Carlos
     * @Date: 2018/4/25 8:18
     * @Description: 初始化列表视图
     **/
    private void initListView() {


    }


    @Override
    public int getLayout() {
        return R.layout.me_fanfollow_activity;
    }



}
