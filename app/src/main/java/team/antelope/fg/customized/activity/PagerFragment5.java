package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

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
import team.antelope.fg.customized.adapter.DzRecyclerAdapterImgUrl;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.business.CustmoizedBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;

/**
 * Created by Kyrene on 2017/12/18.
 */

/**
 * @说明 视频制作分类Pager
 * @创建日期 2018/4/9 下午5:45
 */
public class PagerFragment5 extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private DzRecyclerAdapterImgUrl adapter;
    private List<Long> skillid; //技能ID集合
    private List<String> lists; //标题集合
    private List<String> resids;  //图片资源集合
    private List<String> contents;  //内容介绍集合
    private List<String> type;  //技能类型集合
    private List<String> startdate; //开始时间集合
    private List<String> stopdate;  //结束时间集合
    private List<Long> userid;    //用户ID集合
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected List<PublishSkill> publishSkills;

    /**
     * @说明 点击事件
     * @创建日期 2018/4/9 下午5:38
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * @说明 初始视图等界面控件
     * @创建日期 2018/4/9 下午5:39
     */
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        //技能id集合
        skillid = new ArrayList();
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

        mRecyclerView=view.findViewById(R.id.recyclerView);

        initLayoutView();

    }

    /**
     * @说明 获取布局文件id
     * @创建日期 2018/4/9 下午5:39
     */
    @Override
    protected int getLayoutId() {
        return R.layout.lx_pager_fragment1;
    }

    /**
     * @说明 初始化
     * @创建日期 2018/4/9 下午5:40
     */
    @Override
    protected void init() {

    }

    /**
     * @说明 初始化布局视图
     * @创建日期 2018/4/9 下午5:40
     */
    private void initLayoutView() {
        String endUrl = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.GETCUSTOMIZEDSKILLENDPATH);
        Observable<List<PublishSkill>> observable = RetrofitServiceManager.getInstance()
                .create(CustmoizedBusiness.class).getList(endUrl,
                        SkillAboutDetails.SKILLTYPE5).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<List<PublishSkill>>() {
            @Override
            public void onCompleted() {
                L.i("1234", "complete123");
                L.i("1234", "publishSkills:"+publishSkills);

                if (!publishSkills.isEmpty()){
                    //循环添加集合元素
                    for (int i=0; i<publishSkills.size(); i++) {
                        skillid.add(publishSkills.get(i).getId());
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
                adapter = new DzRecyclerAdapterImgUrl(getActivity(),lists,resids);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new DzRecyclerAdapterImgUrl.OnItemClickListener() {
                    //单击事件
                    @Override
                    public void ItemClickListener(View view, int postion) {
                        Intent intent=new Intent();
                        intent.putExtra("skillid",skillid.get(postion));
                        intent.putExtra("title",lists.get(postion));
                        intent.putExtra("contents",contents.get(postion));
                        intent.putExtra("skilltype",type.get(postion));
                        intent.putExtra("startdate",startdate.get(postion));
                        intent.putExtra("stopdate",stopdate.get(postion));
                        intent.putExtra("userid",userid.get(postion));
                        intent.putExtra("skillpic",resids.get(postion));    //新增的传递的图片
                        intent.setClass(getActivity(),SkillDetails.class);  //指定传递对象
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
