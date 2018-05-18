package team.antelope.fg.publish.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.entity.PersonSkill;
import team.antelope.fg.publish.adapter.PublishItemsAdapter;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.business.PublishBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.PropertiesUtil;

/**
*@Author: lry
*@Date: 2017/12/17 22:56
*@Description: 发布技能fragment
*/

public class PublishFragmentSkill extends BaseFragment {
    ListView lv_skill;
    PublishItemsAdapter skillItemsAdapter;
    ArrayList<HashMap<String,Object>> listItem;
    List<PersonSkill> personSkills;
    private Properties mProp;

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
        lv_skill = (ListView) layout.findViewById(R.id.publish_lv);
        setskillitem();
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
        setskillitem();
    }
    public void setskillitem(){
        mProp = PropertiesUtil.getInstance();
        Observable<List<PersonSkill>> observable = RetrofitServiceManager.getInstance()
                .create(PublishBusiness.class).getAllPersonSkill(mProp.getProperty("getAllPublishSkillEndPath")).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<List<PersonSkill>>() {
            @Override
            public void onCompleted() {
//                view.setvisibililty(view.gone);  设置加载图片消失

                if (personSkills != null){
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
                            Log.v("Publishskill","你点击了ListView条目"+position);  //在LogCat中输出信息
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<PersonSkill> ps) {
                personSkills = ps;
            }
        }));
    }

    /**
     * @Description 订阅
     * @date 2018/1/5
     */
    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
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
}
