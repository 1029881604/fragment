package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.customized.activity.SkillDetails;
import team.antelope.fg.customized.adapter.DzRecyclerAdapterImgUrl;
import team.antelope.fg.customized.constant.SkillAboutDetails;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.adapter.MeNeedAdapter;
import team.antelope.fg.me.adapter.MeSubAdapter;
import team.antelope.fg.me.adapter.MeSubAdapterImgUrl;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.business.CustmoizedBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：Carlos
 * @Date： 2018/5/16 11:46
 * @Description: 我的定制
 **/
public class MeSubActivity extends BaseActivity {
    Toolbar mToolbar;
    private  long id;
    private Properties mProp;
    private List<PublishSkill> pnList;
    private RecyclerView mRecyclerView;
    private MeSubAdapterImgUrl adapter;
    private List<String> lists; //标题集合
    private List<String> resids;  //图片集合
    private List<String> contents;  //内容介绍集合
    private List<String> type;  //技能类型集合
    private List<String> startdate; //开始时间集合
    private List<String> stopdate;  //结束时间集合
    private List<Long> userid;    //用户ID集合
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected List<PublishSkill> publishSkills;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            publishSkills= (List<PublishSkill>) msg.obj;
            initLayoutView();
        }
    };
    @Override
    protected void initView(Bundle savedInstanceState) {
        //文字信息集合
        lists = new ArrayList();
        //内容介绍信息集合
        contents = new ArrayList();
        //技能类型信息集合
        type = new ArrayList();
        //开始时间信息集合
        startdate = new ArrayList();
        //结束时间信息集合
        stopdate = new ArrayList();
        //用户ID集合
        userid = new ArrayList();
        //资源id集合
        resids = new ArrayList();

        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);


       mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("我的定制");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UserDaoImpl userDao = new UserDaoImpl(MeSubActivity.this);
        User ur = userDao.queryAllUser().get(0);
        id = ur.getId();
        sendOkHttpRequest();
    }

    private void sendOkHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(AccessNetConst.BASEPATH)
                            +mProp.getProperty(MeAccessNetConst.getSkillsByPersonEndPath);
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
        pnList = gson.fromJson(responseData, new TypeToken<List<PublishSkill>>() {
        }.getType());

        for (PublishSkill publishSkill : pnList) {

            Log.d("JSONActivity", "id is" + publishSkill.getImg());
        }

    }
    private void initLayoutView() {
        for (int i=0; i<publishSkills.size(); i++) {
            lists.add(publishSkills.get(i).getTitle());
            contents.add(publishSkills.get(i).getContent());
            type.add(publishSkills.get(i).getSkillType());
            startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
            stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
            userid.add(publishSkills.get(i).getuId());
            resids.add(publishSkills.get(i).getImg());
        }
                //recyclerView的相关设置,绑定适配器
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                //设置RecyclerView布局管理器为2列垂直排布
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new MeSubAdapterImgUrl(MeSubActivity.this,lists,resids);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new MeSubAdapterImgUrl.OnItemClickListener() {
                    //单击事件
                    @Override
                    public void ItemClickListener(View view, int postion) {
                        Intent intent=new Intent();
                        intent.putExtra("title",lists.get(postion));
                        intent.putExtra("contents",contents.get(postion));
                        intent.putExtra("skilltype",type.get(postion));
                        intent.putExtra("startdate",startdate.get(postion));
                        intent.putExtra("stopdate",stopdate.get(postion));
                        intent.putExtra("userid",userid.get(postion));
                        intent.putExtra("skillpic",resids.get(postion));    //新增的传递的图片
                        intent.setClass(MeSubActivity.this,MeSkillDetailActivity.class);  //指定传递对象
                        startActivity(intent);
//                ToastUtil.showCustom(getActivity().getApplicationContext(),"点击了："+postion, 2000);
                    }
                    //长按事件
                    @Override
                    public void ItemLongClickListener(View view, int postion) {
                        //长按删除
//                lists.remove(postion);
//                adapter.notifyItemRemoved(postion);
                    }
                });
    }






    @Override
    public int getLayout() {
        return R.layout.me_sub_activity;
    }
}
