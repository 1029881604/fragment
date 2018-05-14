package team.antelope.fg.ui.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericliu.asyncexpandablelist.async.AsyncExpandableListView;
import com.ericliu.asyncexpandablelist.async.AsyncHeaderViewHolder;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.constant.LocationConst;
import team.antelope.fg.constant.SkillAndNeedConst;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.SkillPreInfo;
import team.antelope.fg.ui.activity.NeedInfoActivity;
import team.antelope.fg.ui.activity.PersonInfoActivity;
import team.antelope.fg.ui.dialog.CustomProgressDialog;
import team.antelope.fg.ui.presenter.INearbyAsyncPresenter;
import team.antelope.fg.ui.view.INearbyAsyncView;
import team.antelope.fg.util.L;
import team.antelope.fg.util.NetUtil;
import team.antelope.fg.util.ToastUtil;

/**
 * @Author：hwc
 * @Date：2018/1/3 18:56
 * @Desc: ...
 */

public abstract class BaseNearByActivity extends BaseActivity implements INearbyAsyncView {
    public boolean isNetConnect;           //检测网络是否连接
    public View error_view;        //网络错误显示视图
    public View error_server;        //服务器端异常
    public Dialog loadingDialog;       //Iview控件dialog
    public NetStateReceiver mBroadcastReceiver;
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    public INearbyAsyncPresenter mPresenter;       //代理presenter
    public String type; //skill or need type
    public double longitude;    // user's longitude
    public double latitude;     // user's latitude
    public View view_tags[];    //tags
    public int index;           // tags's index

    /**
     * @Author hwc
     * @Date 2017/12/27
     * @TODO NetStateReceiver 网络监测receiver广播接收者
     *
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        error_view = findViewById(R.id.layout_net_error);
        error_server = findViewById(R.id.layout_server_error);
        error_view.setVisibility(View.GONE);
        error_server.setVisibility(View.GONE);
        mBroadcastReceiver = new NetStateReceiver();
        isNetConnect = NetUtil.isConnected(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(SkillAndNeedConst.TYPE);
        latitude = intent.getDoubleExtra(LocationConst.LATITUDE, 0);
        longitude = intent.getDoubleExtra(LocationConst.LONGITUDE, 0);
        L.i("latitudelongitude", "tudes: "+FgApp.getInstance().tudes);
        if (!FgApp.getInstance().tudes.isEmpty()){
            latitude = FgApp.getInstance().tudes.get("latitude");
            longitude = FgApp.getInstance().tudes.get("longitude");
        }

        L.i("TAG", "latitude:" + latitude);
        L.i("TAG", "longitude:" + longitude);
        initPersenter();
    }

    /**
     * 增加头像点击事件，进行页面跳转
     * @param context
     * @param view
     * @param bundle
     */
    protected void addHeadImgClickEvent(final Context context, View view, final Bundle bundle) {
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonInfoActivity.class);
                intent.putExtra(ForwardConst.USERID, bundle.getLong(ForwardConst.USERID, -1));
                startActivity(intent);
            }
        });
    }

    /**
     * 增加itemdetail的点击事件， 进行页面跳转
     * @param  activityType 0 为need， 1为skill
     */
    protected void addNeedDetailClickEvent(final Context context, View view, final Bundle bundle, final int activityType){
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NeedInfoActivity.class);
                if (activityType == 1){
//                    intent = new Intent(context, SkillInfoActivity.class);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected abstract void initPersenter();

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
    public class NetStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //获取联网状态的NetworkInfo对象
                NetworkInfo info = intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    //如果当前的网络连接成功并且网络连接可用
                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI
                                || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            error_view.setVisibility(View.GONE);
                            if(mPresenter != null && type != null){
                                L.i("whoami", "latitude:"+latitude+" longitude:" + longitude);
                                if("AsyncNeedActivity".equals(this.getClass().getSimpleName())){
                                    mPresenter.getServerNeedData(type, latitude, longitude);
                                } else if("AsyncSkillActivity".equals(this.getClass().getSimpleName())){
                                    mPresenter.getServerSkillData(type, latitude, longitude);
                                }
                            }
                        }
                    } else {
                        error_view.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    /**
     * @Description 在onResume中注册广播
     * @date 2017/12/27
     */
    @Override
    protected void onResume() {
        super.onResume();
        isNetConnect = NetUtil.isConnected(this);   //获取网络连接状态
        //实例化BroadcastReceiver子类 &  IntentFilter
        IntentFilter filter = new IntentFilter();
        //设置接收广播的类型
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * @Description 在onPause中注销广播
     * @date 2017/12/27
     */
    @Override
    protected void onPause() {
        super.onPause();
        //销毁在onResume()方法中的广播
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();       //取消订阅
        mPresenter.onDestroy();
        unSubscribe();              //详细信息取消订阅
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    public Dialog showProgressDialog(String str) {
        loadingDialog = CustomProgressDialog.createLoadingDialog(this, str);
        loadingDialog.show();
        return  loadingDialog;
    }

    @Override
    public void hideProgressDialog() {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    //空实现，适配器
    @Override
    public void showNeedListData(List<NeedPreInfo> needs) {}

    @Override
    public void showSkillListData(List<SkillPreInfo> skills) {}

    @Override
    public void showDataError() {
//        finish();
        error_server.setVisibility(View.VISIBLE);
        L.i("whoami", this.getClass().getSimpleName());
    }

    /**
     * @Author hwc
     * @Date 2017/12/27
     * @TODO AsyncSkillActivity  headerViewHolder 静态内部类
     *
     */
    public static class MyHeaderViewHolder extends AsyncHeaderViewHolder implements AsyncExpandableListView.OnGroupStateChangeListener {

        private final ImageView roundImg;
        private final TextView tv_title;
        private final TextView tv_name;
        private final TextView tv_addressdesc;
        private final TextView tv_distance;
        private final ProgressBar mProgressBar;
        private ImageView ivExpansionIndicator;
        private View headView;
        public MyHeaderViewHolder(View v, final int groupOrdinal, AsyncExpandableListView asyncExpandableListView) {
            super(v, groupOrdinal, asyncExpandableListView);
            tv_title = (TextView) v.findViewById(R.id.title);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_addressdesc = (TextView) v.findViewById(R.id.tv_addressdesc);
            tv_distance = (TextView) v.findViewById(R.id.tv_distance);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            mProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            ivExpansionIndicator = (ImageView) v.findViewById(R.id.ivExpansionIndicator);
            roundImg = (ImageView) v.findViewById(R.id.rountImg_head);
            headView = v;
        }


        public View getHeadView() {
            return headView;
        }
        public TextView getTv_title() {
            return tv_title;
        }

        public TextView getTv_name() {
            return tv_name;
        }
        public TextView getTv_addressdesc() {
            return tv_addressdesc;
        }

        public TextView getTv_distance() {
            return tv_distance;
        }

        public ImageView getRoundImg(){
            return roundImg;
        }

        @Override
        public void onGroupStartExpending() {
            mProgressBar.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onGroupExpanded() {
            mProgressBar.setVisibility(View.GONE);
            ivExpansionIndicator.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setImageResource(R.drawable.ic_arrow_up);
        }

        @Override
        public void onGroupCollapsed() {
            mProgressBar.setVisibility(View.GONE);
            ivExpansionIndicator.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setImageResource(R.drawable.ic_arrow_down);
        }
    }
}
