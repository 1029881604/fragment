package team.antelope.fg.publish.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.entity.PersonSkill;
import team.antelope.fg.me.activity.MeSkillDetailActivity;
import team.antelope.fg.publish.adapter.PublishItemsAdapter;
import team.antelope.fg.publish.widget.PublishRefreshableView;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

/**
*@Author: lry
*@Date: 2017/12/17 22:56
*@Description: 发布技能fragment
*/

public class PublishFragmentSkill extends BaseFragment {
    private static final int FALL = 0;
    private static final int SUCCESS=1;
    ListView lv_skill;
    PublishItemsAdapter skillItemsAdapter;
    ArrayList<HashMap<String,Object>> listItem;
    private PublishRefreshableView publish_refresh;

    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    String json= (String) msg.obj;
                    List<PersonSkill> personSkills=new Gson().fromJson(json, new TypeToken<List<PersonSkill>>(){}.getType());
                    L.i("gson","personNeeds.size "+personSkills.size());
                    setskillitem(personSkills);
                    break;
                case FALL:
                    Toast.makeText(getContext(),"网络错误，技能获取失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.publish_fragment_listview;
    }
    /**
     *@Description: 初始化界面操作
     *@Date: 2017/12/26 18:25
     */
    @Override
    protected void initView(View layout, Bundle savedInstanceState) {
        lv_skill =layout.findViewById(R.id.publish_lv);
        publish_refresh=layout.findViewById(R.id.publish_refresh);
        sendRequest();
        publish_refresh.setOnRefreshListener(new PublishRefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendRequest();
                publish_refresh.finishRefreshing();
            }
        }, 0);
    }
    /**
     *@Description: 初始化视图处理事件
     *@Date: 2017/12/26 18:26
     */
    @Override
    protected void init() {
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private void setskillitem(final List<PersonSkill> personSkills) {
        if(personSkills==null){
            return;
        }
            listItem=new  ArrayList<>();
            for (int i=0;i<personSkills.size();i++){
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("head",personSkills.get(i).getHeadimg());
                map.put("username", personSkills.get(i).getName());
                map.put("isonline",personSkills.get(i).isIsonline());
                map.put("dingwei",personSkills.get(i).getAddressdesc());
                map.put("detail",personSkills.get(i).getContent());
                map.put("fbtime", DateUtil.formatDate(personSkills.get(i).getPublishdate().getTime()));
                listItem.add(map);
            }
            skillItemsAdapter= new PublishItemsAdapter(getContext(),listItem,false);
            //setListAdapter(simpleAdapter);
            lv_skill.setAdapter(skillItemsAdapter);  //为ListView绑定Adapter
            skillItemsAdapter.notifyDataSetChanged();
            lv_skill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent();
                    intent.putExtra("title",personSkills.get(position).getTitle());
                    intent.putExtra("contents",personSkills.get(position).getContent());
                    intent.putExtra("skilltype",personSkills.get(position).getSkilltype());
                    intent.putExtra("startdate",DateUtil.formatDate(personSkills.get(position).getPublishdate().getTime()));
                    intent.putExtra("stopdate",DateUtil.formatDate(personSkills.get(position).getStopdate().getTime()));
                    intent.putExtra("userid",personSkills.get(position).getUid());
                    intent.putExtra("skillpic",personSkills.get(position).getImg());    //新增的传递的图片
                    intent.setClass(getActivity(),MeSkillDetailActivity.class);  //指定传递对象
                    startActivity(intent);
                }
            });
    }

    private void sendRequest() {
        Properties mProp = PropertiesUtil.getInstance();
        String url = mProp.getProperty(AccessNetConst.BASEPATH)+ mProp.getProperty("getAllPublishSkillEndPath");
        OkHttpClient okHttpClient = OkHttpUtils.createHttpClientBuild().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Message message=handler.obtainMessage();
                message.what=FALL;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Message message=handler.obtainMessage();
                if (resp==null){
                    message.what=FALL;
                }else {
                    message.what=SUCCESS;
                    message.obj=resp;
                }
                handler.sendMessage(message);
            }
        });
    }
}
