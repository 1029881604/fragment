package team.antelope.fg.publish.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.db.dao.impl.PublishNeedDaoImpl;
import team.antelope.fg.entity.PersonNeed;
import team.antelope.fg.publish.adapter.PublishItemsAdapter;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;

/**
*@Author: lry
*@Date: 2017/12/17 23:13
*@Description: Publish
*/

public class PublishFragmentNeed extends BaseFragment {
    ListView lv_need;
    PublishItemsAdapter needItemsAdapter;
    ArrayList<HashMap<String,Object>> listItem;
    PublishNeedDaoImpl publishNeedDao;
    private Properties mProp;
    private CountDownLatch latch = new CountDownLatch(1);
    private List<PersonNeed> personNeeds;

    public CompositeSubscription compositeSubscription = new CompositeSubscription();
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
        lv_need = (ListView) layout.findViewById(R.id.publish_lv);
        try {
            sendRequest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void setneeditem(){
        L.i("gson","setneeditem调用成功");
        listItem=new  ArrayList<>();
        for (int i = 0; i < personNeeds.size(); i++) {
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("username",personNeeds.get(i).getName());
            map.put("isonline",personNeeds.get(i).isIsonline());
            map.put("dingwei",personNeeds.get(i).getAddressdesc());
            map.put("detail",personNeeds.get(i).getContent());
            map.put("isfinished",personNeeds.get(i).isIscomplete());
            map.put("fbtime", DateUtil.formatDate(personNeeds.get(i).getCustomdate().getTime()));
            listItem.add(map);
        }
        L.i("gson","setneeditem Map数据添加成功");
        needItemsAdapter= new PublishItemsAdapter(getContext(),listItem,true);
        //setListAdapter(simpleAdapter);
        lv_need.setAdapter(needItemsAdapter);  //为ListView绑定Adapter
        L.i("gson","setneeditem adapter开启成功");
        lv_need.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MyListViewBase","你点击了ListView条目"+position);  //在LogCat中输出信息
            }
        });

    }
    private void sendRequest() throws InterruptedException {
        L.i("gson","Need请求发出");
        new Thread(new Runnable() {
            @Override
            public void run() {
                L.i("gson","Need线程开启");
                try{
                    mProp = PropertiesUtil.getInstance();
                    String url = mProp.getProperty(AccessNetConst.BASEPATH)+ mProp.getProperty("getAllPublishNeedEndPath");
                    L.i("gson",url);
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Gson gson=new Gson();
                    personNeeds=gson.fromJson(responseData, new TypeToken<List<PersonNeed>>(){}.getType());
                    L.i("gson","Need,try开启");
                    L.i("gson","personNeeds.size="+personNeeds.size());
                    latch.countDown();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        latch.await();
        setneeditem();
    }
}
