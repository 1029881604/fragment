package team.antelope.fg.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.db.dao.IAttentionDao;
import team.antelope.fg.db.dao.IPersonDao;
import team.antelope.fg.db.dao.impl.AttentionDaoImpl;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PrivateMessageDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.MeFollowListAdapter;
import team.antelope.fg.me.entity.PersonPinyin;
import team.antelope.fg.me.quickindexbar.QuickIndexBar;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.SetRoundImageViewUtil;


public class MeFollowActivity extends BaseActivity {
    Toolbar mToolbar;
    TextView indexTv, tv_follow_name, tv_show;
    ImageView iv_follow_user_head;
    ListView listView;
    ArrayList<PersonPinyin> personPinyins;
    ArrayList<PersonPinyin> after_person;
    private TextView tv_center;
    MeFollowListAdapter meFollowListAdapter;
    List<Person> personList;
    Long person_id;
    List<Person> psList;
  List<Person> after_psList;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            after_person= (ArrayList<PersonPinyin>) msg.obj;
            meFollowListAdapter = new MeFollowListAdapter(MeFollowActivity.this, after_person);
            listView.setAdapter(meFollowListAdapter);



        }
    };



    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("关注");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_follow_name = findViewById(R.id.tv_follow_name);
        iv_follow_user_head = findViewById(R.id.iv_follow_user_head);
        listView = findViewById(R.id.listView);
        tv_center = (TextView) findViewById(R.id.tv_center);
        QuickIndexBar quickIndexBar = findViewById(R.id.quick_bar);
        quickIndexBar.setListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
//                Toast.makeText(getApplicationContext(),letter,Toast.LENGTH_SHORT).show();
                showLetter(letter);
                // 根据字母定位ListView, 找到集合中第一个以letter为拼音首字母的对象,得到索引
                for (int i = 0; i < personPinyins.size(); i++) {
                    String size= String.valueOf(personPinyins.size());
                    Log.i("personPinyins.size()",size);
                    PersonPinyin personPinyin = personPinyins.get(i);
                    String l = personPinyin.getPinyin().charAt(0) + "";
                    String eng=personPinyin.getName().charAt(0)+"";
                    if(TextUtils.equals(letter, l)||TextUtils.equals(letter, eng)){
                        // 匹配成功
                        Log.i("kkkkkkkkkk","成功匹配");
                        listView.setSelection(i);
                        break;
                    }
                }

            }
        });
        User user = new UserDaoImpl(this).queryAllUser().get(0);
        person_id =user.getId();
        sendOkHttpRequest();
        initListView();
        initListEvent();




    }

    /**
     //     * 显示字母
     //     * @param letter
     //     */
    protected void showLetter(String letter) {
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(letter);

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_center.setVisibility(View.GONE);
            }
        }, 2000);

    }
/**
 * @Author：Carlos
 * @Date:  2018/4/18 10:17
 * @Description:  建立连接
 **/
    private void sendOkHttpRequest() {

        new  Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("person_id", String.valueOf(person_id))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.137.1:8080/fragment_server/PostPersonFriendsServlet")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData= response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message=new Message();
                    personPinyins =new ArrayList<PersonPinyin>();
                    fillAndSortData(personPinyins);
                    message.obj=personPinyins;
                    IPersonDao personDao = new PersonDaoImpl(MeFollowActivity.this);
                    for (Person person: psList){
                    personDao.insert(person);
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    /**
     * @Author：Carlos
     * @Date:  2018/4/17 10:15
     * @Description: 填充数据排序
     **/
    private void fillAndSortData(ArrayList<PersonPinyin> personPinyins) {
        for (Person person: psList){
            String name=person.getName();
            String head=person.getHeadImg();
            Long personId=person.getId();
            personPinyins.add(new PersonPinyin(name,head,personId));
            Log.d("JSONActivity","id is"+person.getId());
            Log.d("JSONActivity","name is"+person.getName());
        }
        Collections.sort(personPinyins);
    }

/**
 * @Author：Carlos
 * @Date:  2018/4/18 10:19
 * @Description:  解析Json
 **/
    private void parseJSONWithGSON(String responseData) {

        Gson gson = new Gson();
        psList = gson.fromJson(responseData, new TypeToken<List<Person>>()
        {}.getType());
        for (Person person: psList){

            Log.d("JSONActivity","id is"+person.getId());
            Log.d("JSONActivity","name is"+person.getName());
            Log.d("JSONActivity","version is"+person.getEmail());
        }
    }

    private void initListEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_personId = view.findViewById(R.id.tv_personId);
                String personId = tv_personId.getText().toString();
                Intent intent =new Intent(MeFollowActivity.this,MePersonActivity.class);
                intent.putExtra("person_id",Long.parseLong(personId));
                startActivity(intent);
            }
        });

    }



    private void initListView() {

//        User user = new UserDaoImpl(this).queryAllUser().get(0);
//        AttentionDaoImpl attentionDao = new AttentionDaoImpl(this);
//        personList = psList;



//
//        for (int i = 0; i < 2; i++) {
//            ImagePicture head1 = new ImagePicture("j",R.drawable.me_user_head5);
//            imagePictureList.add(head1);
//            ImagePicture head2 = new ImagePicture("j",R.drawable.me_user_head3);
//            imagePictureList.add(head2);
//            ImagePicture head3 = new ImagePicture("j",R.drawable.me_user_head4);
//            imagePictureList.add(head3);

//
//        }
    }


    @Override
    public int getLayout() {
        return R.layout.me_follow_activity;
    }



}
