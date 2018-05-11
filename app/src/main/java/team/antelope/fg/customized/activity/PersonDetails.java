package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.customized.constant.AccessNetConst;
import team.antelope.fg.customized.constant.SkillAboutDetails;
import team.antelope.fg.customized.adapter.DzRecyclerAdapter;
import team.antelope.fg.customized.adapter.DzRecyclerAdapterImgUrl;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.business.CustmoizedBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

/**
 * Created by Kyrene on 2017/12/18.
 */

public class PersonDetails extends BaseActivity{

    Toolbar mToolbar;
    Bitmap bitmap1,bitmap2;
    TextView personName;
    TextView personSex;
    TextView personAge;
    TextView personEmail;
    TextView PersonDealNum;
    TextView PersonFansNum;
    SetRoundImageViewUtil setRoundImageViewUtil;

    private RecyclerView mRecyclerView;
    private DzRecyclerAdapterImgUrl adapter;
    private List<String> lists; //标题集合
    private List<String>  resids;  //图片集合
    private List<String> contents;  //内容介绍集合
    private List<String> type;  //技能类型集合
    private List<String> startdate; //开始时间集合
    private List<String> stopdate;  //结束时间集合
    private List<Long> userid;    //用户ID集合


    protected List<PublishSkill> publishSkills;
    Person mPerson;  //人物实例
    public CompositeSubscription compositeSubscription = new CompositeSubscription();

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

        mRecyclerView = findViewById(R.id.recyclerView);


        mToolbar = (Toolbar) findViewById(R.id.toolbarPerson);
        setRoundImageViewUtil=findViewById(R.id.iv_user_head);

        personName =findViewById(R.id.tv_name);
        personSex =findViewById(R.id.tv_sex);
        personAge =findViewById(R.id.tv_age);
        personEmail =findViewById(R.id.tv_email);
        PersonDealNum=findViewById(R.id.tv_dealNum);
        PersonFansNum =findViewById(R.id.tv_fanNum);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//mToolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initLayoutView();
        initRecyclerView();
    }

    /**
    * @说明 人物信息
    * @创建日期 2018/1/8 上午6:01
    */
    private void initLayoutView() {
        long personId = getIntent().getLongExtra("person_id",0l);
        //从服务端获取person对象读取数据库
        String endUrl = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.GETPERSONDETAILSENDPATH);
        Observable<Person> observable = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).getPerson(endUrl, personId
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);

//        //从服务端获取publishlist对象读取数据库
//        String endUrl1 = PropertiesUtil.getInstance().
//                getProperty(AccessNetConst.GETSKILLSBYPERSONENDPATH);
//        Observable<List<PublishSkill>> observable1 = RetrofitServiceManager.getInstance()
//                .create(CustmoizedBusiness.class).getListByPerson(endUrl1, personId
//                ).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        /**
         * @说明 显示与人物相关信息
         * @创建日期 2018/5/10 上午6:04
         */
        addSubscription(observable.subscribe(new Subscriber<Person>() {
            @Override
            public void onCompleted() {
                L.i("persondetails","111111111111");
                L.i("person",mPerson.toString());

//                L.i("testforpersonimg",mPerson.getHeadImg());
                mToolbar.setTitle(mPerson.getName());
                personName.setText(mPerson.getName());
                personSex.setText(mPerson.getSex());
                personAge.setText(String.valueOf(mPerson.getAge()) );
                personEmail.setText(mPerson.getEmail());
                PersonDealNum.setText(String.valueOf(mPerson.getDealnum()));
                PersonFansNum.setText(String.valueOf(mPerson.getFansnum()) );

                /**
                 * @说明 设置图片
                 * @创建日期 2018/5/10 下午10:07
                 */
                RequestOptions options = new RequestOptions();
                GlideApp.with(PersonDetails.this)
                        .load(mPerson.getHeadImg())
                        .placeholder(R.mipmap.default_avatar400)
                        .error(R.drawable.ic_launcher_round)
                        .apply(options)
                        .into(setRoundImageViewUtil);
            }

            @Override
            public void onError(Throwable e) {
                L.i("persondetails", "onError");
            }

            @Override
            public void onNext(Person person) {
                mPerson = person;
                L.i("persondetails", "onNext1");
            }
        }));

//        /**
//         * @说明 利用recyclerView显示与人物相关的技能信息
//         * @创建日期 2018/5/10 上午6:04
//         */
//        addSubscription(observable1.subscribe(new Subscriber<List<PublishSkill>>() {
//            @Override
//            public void onCompleted() {
//                L.i("1234", "complete123");
//                L.i("1234", "publishSkills:"+publishSkills);
//
//                //循环添加集合元素
//                for (int i=0; i<publishSkills.size(); i++) {
//                    if (!publishSkills.isEmpty()){
//                        lists.add(publishSkills.get(i).getTitle());
//                        contents.add(publishSkills.get(i).getContent());
//                        type.add(publishSkills.get(i).getSkillType());
//                        startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
//                        stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
//                        userid.add(publishSkills.get(i).getuId());
//                        resids.add(publishSkills.get(i).getImg());
//                    }
//                }
//
//                //recyclerView的相关设置,绑定适配器
//                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                //设置RecyclerView布局管理器为2列垂直排布
//                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                adapter = new DzRecyclerAdapterImgUrl(PersonDetails.this,lists,resids);
//                mRecyclerView.setAdapter(adapter);
//                adapter.setOnClickListener(new DzRecyclerAdapterImgUrl.OnItemClickListener() {
//                    //单击事件
//                    @Override
//                    public void ItemClickListener(View view, int postion) {
//                        Intent intent=new Intent();
//                        intent.putExtra("title",lists.get(postion));
//                        intent.putExtra("contents",contents.get(postion));
//                        intent.putExtra("skilltype",type.get(postion));
//                        intent.putExtra("startdate",startdate.get(postion));
//                        intent.putExtra("stopdate",stopdate.get(postion));
//                        intent.putExtra("userid",userid.get(postion));
//                        intent.putExtra("skillpic",resids.get(postion));    //新增的传递的图片
//                        intent.setClass(PersonDetails.this,SkillDetails.class);  //指定传递对象
//                        startActivity(intent);
////                ToastUtil.showCustom(getActivity().getApplicationContext(),"点击了："+postion, 2000);
//                    }
//                    //长按事件
//                    @Override
//                    public void ItemLongClickListener(View view, int postion) {
//                        //长按删除
////                lists.remove(postion);
////                adapter.notifyItemRemoved(postion);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                L.i("mytag", "onError");
////                        loadable = false;
////                        loadData();
//            }
//
//            @Override
//            public void onNext(List<PublishSkill> list) {
//                publishSkills = list;
//                L.i("mytag", "onnext123");
//                //循环添加集合元素
//
//            }
//        }));

    }

    /**
    * @说明 利用recyclerView显示与人物相关的技能信息
    * @创建日期 2018/1/8 上午6:04
    */
    private void initRecyclerView(){
        long personId = getIntent().getLongExtra("person_id",0l);
        String endUrl1 = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.GETSKILLSBYPERSONENDPATH);
        Observable<List<PublishSkill>> observable = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).getListByPerson(endUrl1, personId
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<List<PublishSkill>>() {
            @Override
            public void onCompleted() {
                L.i("1234", "complete123");
                L.i("1234", "publishSkills:"+publishSkills);

                if (!publishSkills.isEmpty()){
                    //循环添加集合元素
                    for (int i=0; i<publishSkills.size(); i++) {
                        lists.add(publishSkills.get(i).getTitle());
                        contents.add(publishSkills.get(i).getContent());
                        type.add(publishSkills.get(i).getSkillType());
                        startdate.add(DateUtil.formatDate(publishSkills.get(i).getPublishDate().getTime()));
                        stopdate.add(DateUtil.formatDate(publishSkills.get(i).getStopDate().getTime()));
                        userid.add(publishSkills.get(i).getuId());
                        resids.add(publishSkills.get(i).getImg());
                    }
                }

                //recyclerView的相关设置,绑定适配器
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                //设置RecyclerView布局管理器为2列垂直排布
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new DzRecyclerAdapterImgUrl(PersonDetails.this,lists,resids);
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
                        intent.setClass(PersonDetails.this,SkillDetails.class);  //指定传递对象
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


    @Override
    public int getLayout() {
        return R.layout.lx_persondetails;
    }

    /**
     * @Description 取消订阅
     * @date 2018/1/5
     */
    public void unSubscribe(){
        if (compositeSubscription.hasSubscriptions()) {
            if (!compositeSubscription.isUnsubscribed()) {
                compositeSubscription.unsubscribe();
                compositeSubscription.clear();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
}
