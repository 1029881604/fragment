package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.customized.constant.SkillAboutDetails;
import team.antelope.fg.customized.adapter.DzRecyclerAdapter;
import team.antelope.fg.customized.adapter.DzRecyclerAdapterImgUrl;
import team.antelope.fg.customized.searchview.ICallBack;
import team.antelope.fg.customized.searchview.SearchView;
import team.antelope.fg.customized.searchview.bCallBack;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.business.CustmoizedBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.ToastUtil;

/**
 * Created by Kyrene on 2018/1/6.
 */

public class SearchResult extends BaseActivity {

//    PublishSkillDaoImpl publishSkillDao;
    List<PublishSkill> publishSkills;
    private DzRecyclerAdapterImgUrl adapter;
    private List<String> lists; //标题集合
    private List<String>  resids;  //图片集合
    private List<String> contents;  //内容介绍集合
    private List<String> type;  //技能类型集合
    private List<String> startdate; //开始时间集合
    private List<String> stopdate;  //结束时间集合
    private List<Long> userid;    //用户ID集合

    public CompositeSubscription compositeSubscription = new CompositeSubscription();

//    private SearchView msearchView;
    private Button back_button;
    private Button search_button;
    private RecyclerView mRecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {

//        TextView tv=(TextView) findViewById(R.id.test1);
//        publishSkillDao=new PublishSkillDaoImpl(this);
//        publishSkills=publishSkillDao.queryAllPublishSkill();
        mRecyclerView = findViewById(R.id.recyclerView_resultClass);

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


//        for (int i=0; i<publishSkills.size(); i++){
//            /**
//             * @说明 设置中间字符串(transform全大写)解决字母大小写问题
//             * @创建日期 2018/1/7 上午12:50
//             */
//            String dbTitleString=publishSkills.get(i).getTitle();
//            String dbContentString=publishSkills.get(i).getContent();
//            dbTitleString=dbTitleString.toUpperCase();
//            dbContentString=dbContentString.toUpperCase();
//            searchWord=searchWord.toUpperCase();
//
//            if (!(searchWord==null||searchWord.isEmpty())&&(judgeContains(dbTitleString,searchWord)||
//                    judgeContains(dbContentString,searchWord))){
//
//                lists.add(publishSkills.get(i).getTitle());
//                contents.add(publishSkills.get(i).getContent());
//                type.add(publishSkills.get(i).getSkillType());
//                startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
//                stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
//                userid.add(publishSkills.get(i).getuId());
//            }
//
//        }
//        if(searchWord==null||searchWord.isEmpty()||
//                !judgeContains(dbTitleString,searchWord)||
//                !judgeContains(dbContentString,searchWord)){
//            startActivity(new Intent(SearchResult.this,SearchMismatching.class));
//        }

//        resids.add(R.drawable.lx_fgpic1);
//        resids.add(R.drawable.lx_fgpic2);
//        resids.add(R.drawable.lx_fgpic3);
//        resids.add(R.drawable.lx_fgpic4);
//        resids.add(R.drawable.lx_fgpic5);
//        resids.add(R.drawable.lx_fgpic1);
//        resids.add(R.drawable.lx_fgpic2);
//        resids.add(R.drawable.lx_fgpic3);
//        resids.add(R.drawable.lx_fgpic4);
//        resids.add(R.drawable.lx_fgpic5);
//        tv.setText(searchWord);

//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //设置RecyclerView布局管理器为2列垂直排布
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//
//        adapter = new DzRecyclerAdapterImgUrl(SearchResult.this,lists,resids);
//        mRecyclerView.setAdapter(adapter);
//        adapter.setOnClickListener(new DzRecyclerAdapterImgUrl().OnItemClickListener() {
//            //单击事件
//            @Override
//            public void ItemClickListener(View view, int postion) {
//                Intent intent1=new Intent();
//                intent1.putExtra("title",lists.get(postion));
//                intent1.putExtra("contents",contents.get(postion));
//                intent1.putExtra("skilltype",type.get(postion));
//                intent1.putExtra("startdate",startdate.get(postion));
//                intent1.putExtra("stopdate",stopdate.get(postion));
//                intent1.putExtra("userid",userid.get(postion));
//                intent1.setClass(SearchResult.this,SkillDetails.class);  //指定传递对象
//                startActivity(intent1);
////                ToastUtil.showCustom(getActivity().getApplicationContext(),"点击了："+postion, 2000);
//            }
//            //长按事件
//            @Override
//            public void ItemLongClickListener(View view, int postion) {
//                //长按删除
////                lists.remove(postion);
////                adapter.notifyItemRemoved(postion);
//            }
//        });
        initLayoutView();
        backAndSearchMethod();
    }


    /**
     * @说明 初始化布局视图
     * @创建日期 2018/4/9 下午5:40
     */
    private void initLayoutView() {

        Intent intent=getIntent();
        final String searchWord = intent.getStringExtra("searchword");

        String endUrl = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.SEARCHSKILLSENDPATH);
        Observable<List<PublishSkill>> observable = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).searchResult(endUrl, searchWord)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<List<PublishSkill>>() {
            @Override
            public void onCompleted() {
                L.i("1234", "complete123");
                L.i("1234", "publishSkills:"+publishSkills);

                if (!publishSkills.isEmpty()) {
                    for (int i = 0; i < publishSkills.size(); i++) {
//                    /**
//                     * @说明 设置中间字符串(transform全大写)解决字母大小写问题
//                     * @创建日期 2018/1/7 上午12:50
//                     */
//                    String dbTitleString=publishSkills.get(i).getTitle();
//                    String dbContentString=publishSkills.get(i).getContent();
//                    dbTitleString=dbTitleString.toUpperCase();
//                    dbContentString=dbContentString.toUpperCase();
//                    searchWord = searchWord.toUpperCase();

//                    if (!(searchWord[0] ==null|| searchWord[0].isEmpty())&&(judgeContains(dbTitleString, searchWord[0])||
//                            judgeContains(dbContentString, searchWord[0]))){

                        if (!searchWord.isEmpty()) {
                            lists.add(publishSkills.get(i).getTitle());
                            contents.add(publishSkills.get(i).getContent());
                            type.add(publishSkills.get(i).getSkillType());
                            startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
                            stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
                            userid.add(publishSkills.get(i).getuId());
                            resids.add(publishSkills.get(i).getImg());
                        }

                    }
                }

                //recyclerView的相关设置,绑定适配器
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                //设置RecyclerView布局管理器为2列垂直排布
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new DzRecyclerAdapterImgUrl(SearchResult.this,lists,resids);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new DzRecyclerAdapterImgUrl.OnItemClickListener() {
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
                        intent.setClass(SearchResult.this,SkillDetails.class);  //指定传递对象
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
            public void onError(Throwable e) {
                L.i("mytag", "onError");
//                        loadable = false;
//                        loadData();
            }

            @Override
            public void onNext(List<PublishSkill> list) {
                publishSkills = list;
                L.i("mytag", "onnext123");
                //循环添加集合元素

            }
        }));
    }

//    /**
//     * @Description 取消订阅
//     * @date 2018/1/5
//     */
//    public void unSubscribe(){
//        if (compositeSubscription.hasSubscriptions()) {
//            if (!compositeSubscription.isUnsubscribed()) {
//                compositeSubscription.unsubscribe();
//                compositeSubscription.clear();
//            }
//        }
//    }


    /**
    * @说明 判断字符串m是否包含n，包含返回true，不包含返回false
    * @创建日期 2018/1/6 下午11:41
    */
    protected static boolean judgeContains(String m, String n){

        if (m.contains(n)){
            return true;
        }else {
            return false;
        }
    }

    /**
    * @说明 toolbar中back和search按钮相关事件
    * @创建日期 2018/1/7 上午1:05
    */
    private void backAndSearchMethod(){
        back_button=(Button)findViewById(R.id.back_icon_resultClass);
        search_button=(Button)findViewById(R.id.search_icon_resultClass);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                startActivity(new Intent(SearchResult.this, MainActivity.class));
//                SearchResult.this.overridePendingTransition(R.anim.lx_search_open,R.anim.lx_search_close);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResult.this, SearchActivity.class));
                SearchResult.this.overridePendingTransition(R.anim.lx_search_open,R.anim.lx_search_close);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.lx_activity_searchresult;
    }

    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
}
