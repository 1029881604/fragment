package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.customized.adapter.OrdersRecyclerAdapter;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.db.DBUtil;
import team.antelope.fg.db.dao.IUserDao;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Orders;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.ToastUtil;

/**
* @说明 Create by lx   未支付订单页
* @创建日期 2018/5/20 下午9:46
*/
public class OrdersNotPayActivity extends BaseActivity{

    long id;    //当前用户id
    Toolbar mToolbar;

    RecyclerView mRecyclerView;
    OrdersRecyclerAdapter adapter;
    Properties mProp;

    List<Orders> orList;

    List<Long> orderID; //订单表ID
    List<Long> uID;     //当前用户ID
    List<Long> uID_s;  //技能拥有者ID
    List<Long> skillID;  //技能ID
    List<String> skillTitle;  //技能标题
    List<String> skillContent; //技能内容
    List<String> skillPic;  //技能图片
    List<String> skillType; //技能类型
    List<String> createTime;    //创建时间
    List<String> isDelete;    //是否删除
    List<String> isPay;     //是否删除
    List<String> isComment;  //是否评论

    List<Orders> orders;

    private SwipeRefreshLayout swipeRefreshLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                orders= (List<Orders>) msg.obj;
                initLayoutView();
                swipeRefreshLayout.setRefreshing(false);
            }else{
                ToastUtil.showCustom(OrdersNotPayActivity.this,"请检查网络连接",3000);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {

        orderID = new ArrayList<>();
        uID = new ArrayList<>();
        uID_s = new ArrayList<>();
        skillID = new ArrayList<>();
        skillTitle = new ArrayList<>();
        skillContent = new ArrayList<>();
        skillPic = new ArrayList<>();
        skillType = new ArrayList<>();
        createTime = new ArrayList<>();
        isDelete = new ArrayList<>();
        isPay = new ArrayList<>();
        isComment = new ArrayList<>();

        mRecyclerView = findViewById(R.id.orderRecyclerView1);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_order);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderID.clear();
                uID.clear();
                uID_s.clear();
                skillID.clear();
                skillTitle.clear();
                skillContent.clear();
                skillPic.clear();
                skillType.clear();
                createTime.clear();
                isDelete.clear();
                isPay.clear();
                isComment.clear();
                adapter.notifyDataSetChanged();
                refreshSkills();
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("待付款");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        IUserDao userDao = new UserDaoImpl(this);
        User ur = userDao.queryAllUser().get(0);
        id = ur.getId();
        sendOkHttpRequest();
    }

    private void initLayoutView() {
        if (orders != null) {
            for (int i = 0; i < orders.size(); i++) {
                orderID.add(orders.get(i).getId());
                uID.add(orders.get(i).getUid());
                uID_s.add(orders.get(i).getUid_s());
                skillID.add(orders.get(i).getSkillid());
                skillTitle.add(orders.get(i).getTitle());
                skillContent.add(orders.get(i).getContent());
                skillPic.add(orders.get(i).getImg());
                skillType.add(orders.get(i).getSkilltype());
                createTime.add(DateUtil.formatDate(orders.get(i).getCreate_time().getTime()));
                isDelete.add(String.valueOf(orders.get(i).isIsdelete()));
                isPay.add(String.valueOf(orders.get(i).isIspay()));
                isComment.add(String.valueOf(orders.get(i).isIscomment()));
            }
        }
        //recyclerView的相关设置,绑定适配器
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置RecyclerView布局管理器为2列垂直排布
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new OrdersRecyclerAdapter(this,orderID, skillPic, skillTitle, skillContent);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OrdersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int postion) {
//                Intent intent=new Intent();
//                intent.putExtra("title",lists.get(postion));
//                intent.putExtra("contents",contents.get(postion));
//                intent.putExtra("skilltype",type.get(postion));
//                intent.putExtra("startdate",startdate.get(postion));
//                intent.putExtra("stopdate",stopdate.get(postion));
//                intent.putExtra("userid",userid.get(postion));
//                intent.putExtra("skillpic",resids.get(postion));    //新增的传递的图片
//                intent.setClass(MeCollectionActivity.this,MeSkillDetailActivity.class);  //指定传递对象
//                startActivity(intent);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {

            }

            @Override
            public void ItemDeleteListener(View view, int postion) {

            }

            @Override
            public void ItemPayListener(View view, int postion) {

            }
        });
    }

    private void sendOkHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH)
                            +mProp.getProperty(AccessNetConst.GETORDERSNOTPAYENDPATH);
                    Request request = new Request.Builder()
                            .url(path+"?uid="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    //用于判断网络连接
                    if (responseData == null){
                        message.what = 0;
                    }else{
                        message.what = 1;
                        message.obj = orList;
                    }
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        orList = gson.fromJson(responseData, new TypeToken<List<Orders>>() {}.getType());
    }

    /**
     * @说明 刷新事件
     * @创建日期 2018/6/4 上午10:18
     */
    private void refreshSkills(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(800);

                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(team.antelope.fg.constant.AccessNetConst.BASEPATH)
                            +mProp.getProperty(AccessNetConst.GETORDERSNOTPAYENDPATH);
                    Request request = new Request.Builder()
                            .url(path+"?uid="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    //用于判断网络连接
                    if (responseData == null){
                        message.what = 0;
                    }else{
                        message.what = 1;
                        message.obj = orList;
                    }
                    handler.sendMessage(message);
                }catch (InterruptedException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int getLayout() {
//        return R.layout.lx_order_fragment;
        return R.layout.lx_activity_orders;
    }

}
