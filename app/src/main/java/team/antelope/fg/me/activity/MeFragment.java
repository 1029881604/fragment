package team.antelope.fg.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPersonDao;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.business.MeBusiness;
import team.antelope.fg.ui.business.NearbyBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

import static android.app.Activity.RESULT_OK;
import static android.media.CamcorderProfile.get;


/**
 * Created by Carlos on 2017/12/5.
 **/

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @Nullable
    private TextView tv_message,tv_follow,tv_collecion,
            tv_myneed,tv_freetime,tv_making,tv_setting,tv_name;
    private LinearLayout layout_user_profile;
    private DBOpenHelper dbOpenHelper;
    SetRoundImageViewUtil setRoundImageViewUtil;
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected User mUser;
    protected Person mPerson;

    @Override
    protected void initView(View layout, Bundle savedInstanceState) {
        tv_message = layout.findViewById(R.id.me_message);
        tv_follow = layout.findViewById(R.id.me_follow);
        tv_collecion = layout.findViewById(R.id.me_collection);
        tv_myneed = layout.findViewById(R.id.me_my_need);
        tv_freetime = layout.findViewById(R.id.me_free_time);
        tv_making = layout.findViewById(R.id.me_making);
        tv_setting= layout.findViewById( R.id.me_my_setting);
        tv_name=layout.findViewById(R.id.tv_name);
        layout_user_profile= layout.findViewById(R.id.lay_view_user);
        /*设置圆形头像*/
        setRoundImageViewUtil = layout.findViewById(R.id.me_user_head);
        setRoundImageViewUtil.setImageResource(R.drawable.me_user_head1);
        dbOpenHelper =new DBOpenHelper(getActivity(),"my.db",null,1);
        Log.d("debug","111111111");
    }

    @Override
    public void onResume() {
        super.onResume();
        initLayoutView();
    }


    private void initLayoutView() {
        UserDaoImpl userDao = new UserDaoImpl(getmActivity());
        //取user之前要判断数据库中有没有
        List<User> userList = userDao.queryAllUser();
        User user = null;
        if(userList != null && !userList.isEmpty()){
            user = userList.get(0);
        }
        if(user != null){
            //先判断数据库中有没有好吧
            mUser = user;
            Person person = new PersonDaoImpl(getmActivity()).queryById(user.getId());
            if(person != null){
                mPerson = person;
                tv_name.setText(mPerson.getName());
            } else if(person == null){//如果数据库中没有，则去服务器获取
                String endUrl = PropertiesUtil.getInstance().
                        getProperty(AccessNetConst.GETUSERENDPATH);
                Observable<Person> observable = RetrofitServiceManager.getInstance()
                        .create(MeBusiness.class).getUser(endUrl,
                                mUser.getId()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
                addSubscription(observable.subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
//                view.setvisibililty(view.gone);  设置加载图片消失
                        L.i("mytag", "complete123");
                        L.i("mytag", "mNearbyModularInfo"+mPerson.toString());
                        if (mPerson != null){
                            L.i("executed", "yyyyyyyy");
                            //保存到本地数据库中 start
                            saveData();
//                            //保存到本地数据库中 end
                            tv_name.setText(mPerson.getName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.i("executed", "onError?????");
//                        loadable = false;
//                        loadData();
                    }

                    @Override
                    public void onNext(Person person) {
                        mPerson = person;
                        L.i("executed", "onnext123????");
                    }
                }));
            }
        }
    }

    public void saveData(){
        IPersonDao personDao = new PersonDaoImpl(getmActivity());
        //先看看本地数据库中有没有
        Person p = personDao.queryById(mPerson.getId());
        if(p == null){
            personDao.insert(mPerson);
        }
    }


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

    @Override
    protected int getLayoutId() {
        return R.layout.mefragment_layout;
    }


    @Override
    protected void init() {
        setOnListener();
    }

    private void setOnListener() {
        layout_user_profile.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_making.setOnClickListener(this);
        tv_collecion.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        tv_myneed.setOnClickListener(this);
        tv_freetime.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_view_user:// 个人信息
                String beforename =tv_name.getText().toString();
                Intent intent =new Intent(getActivity(),MeProfileActivity.class);
            intent.putExtra("get_name",beforename);
            startActivityForResult(intent,1);
                break;
            case R.id.me_message:// 消息
            startActivity(new Intent(getActivity(), MessageListActivity.class));
                break;
            case R.id.me_collection://收藏
                startActivity(new Intent(getActivity(), MeCollectionActivity.class));
                break;
            case R.id.me_follow://关注
                startActivity(new Intent(getActivity(), MeFollowActivity.class));
                break;
//            case R.id.me_making://我的定制
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
//            case R.id.me_my_need://我的需求
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
//
//            case R.id.me_free_time://空闲时间
//                startActivity(new Intent(getActivity(), MePublicActivity.class));
//                break;
            case R.id.me_my_setting://设置
                startActivity(new Intent(getActivity(),MeSettingActivity.class));
                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode== RESULT_OK){
                    String returnName= data.getStringExtra("set_name");
                    tv_name.setText(returnName);
                }
                break;
                default:
        }
    }
}


