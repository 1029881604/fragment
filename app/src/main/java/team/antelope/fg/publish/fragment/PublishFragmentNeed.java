package team.antelope.fg.publish.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
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
    private Properties mProp;
    private CountDownLatch latch = new CountDownLatch(1);
    private List<PersonNeed> personNeeds;
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
        sendRequest();
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
        sendRequest();
    }
    public void setneeditem(){
        if(personNeeds == null){
            return ;
        }
        L.i("gson","setneeditem调用成功");
        listItem=new  ArrayList<>();
        for (int i = 0; i < personNeeds.size(); i++) {
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("head",personNeeds.get(i).getHeadimg());
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
    private void sendRequest() {
        L.i("gson","Need请求发出");
        mProp = PropertiesUtil.getInstance();
        String url = mProp.getProperty(AccessNetConst.BASEPATH)+ mProp.getProperty("getAllPublishNeedEndPath");
        L.i("gson",url);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.i("gson","resp error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                String resp = response.body().string();
                L.i("gson","resp "+resp);
                personNeeds=gson.fromJson(resp, new TypeToken<List<PersonNeed>>(){}.getType());
                L.i("gson","personNeeds: "+personNeeds);
                latch.countDown();
            }
        });
        try {
            latch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setneeditem();
    }
}
