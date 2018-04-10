package team.antelope.fg.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ericliu.asyncexpandablelist.CollectionView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.common.GlideApp;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.LocationConst;
import team.antelope.fg.constant.SkillAndNeedConst;
import team.antelope.fg.entity.NearbyInfo;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.activity.AsyncNeedActivity;
import team.antelope.fg.ui.activity.AsyncSkillActivity;
import team.antelope.fg.ui.base.BaseNearbyFragment;
import team.antelope.fg.ui.business.NearbyBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.util.L;
import team.antelope.fg.util.NetUtil;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：hwc
 * @Date：2017/12/20 21:24
 * @Desc: PhotographyFragment  摄影fg
 */

public class AccompanyFragment extends BaseNearbyFragment<String, NearbyInfo>{
    private  NearbyModularInfo mNearbyModularInfo;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mCollectionView = view.findViewById(R.id.collectionView);
        mCollectionView.setCollectionCallbacks(this);
        inventory = new CollectionView.Inventory<>();
    }

    /**
     * @Description 获取布局
     * @date 2017/12/26
     */
    @Override
    protected int getLayoutId() {
        return R.layout.nearby_fragment_use_common;
    }


    @Override
    protected void init() {
        NearbyFragment fg3 = (NearbyFragment) getmActivity().getSupportFragmentManager()
                .findFragmentByTag(MainActivity.fgTags[2]);
        latitude = fg3.latitude;
        longitude = fg3.longitude;
    }

    /**
     * @Description 继承自CollectionViewCallbacks
     * @date 2017/12/20
     */
    @Override
    public void bindCollectionItemView(Context context, RecyclerView.ViewHolder holder,
                                       int groupOrdinal, NearbyInfo item) {
        ObjectsItemHolder itemHolder = (ObjectsItemHolder) holder;
        //打标签
        itemHolder.getTextViewTitle().setTag(item.getTag());
        itemHolder.getTextViewTitle().setText(item.getTitle());
        itemHolder.getTextViewDescrption().setText(item.getBody());
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.mipmap.default_avatar200)
                .error(R.mipmap.error200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();
        GlideApp.with(getmActivity())
                .load(item.getImg())
                .apply(options)
                .into(itemHolder.getIvPreview());
    }

    @Override
    public RecyclerView.ViewHolder newCollectionItemView(Context context, int groupOrdinal, ViewGroup parent) {
        // Create a new view.
        View v = LayoutInflater.from(context)
                .inflate(R.layout.nearby_text_row_item, parent, false);

        final int type = groupOrdinal;
        // 定义点击事件
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = v.findViewById(R.id.title);
                Intent intent = null;
                if(type == FORWARD_SKILL){
                    intent = new Intent(FgApp.getInstance().getApplicationContext(), AsyncSkillActivity.class);
                    intent.putExtra(SkillAndNeedConst.TYPE, SkillAndNeedConst.SKILL_TYPE_ACCOMPANY);
                } else if(type == FORWARD_TASK) {
                    intent = new Intent(FgApp.getInstance().getApplicationContext(), AsyncNeedActivity.class);
                    intent.putExtra(SkillAndNeedConst.TYPE, SkillAndNeedConst.NEED_TYPE_ACCOMPANY);
                }
                intent.putExtra(LocationConst.LATITUDE, latitude);
                intent.putExtra(LocationConst.LONGITUDE, longitude);
                startActivity(intent);
            }
        });
        return new ObjectsItemHolder(v);
    }
    /**
     * @Description 加载数据
     * @date 2018/1/3
     */
    @Override
    public void loadData() {
        inventory = new CollectionView.Inventory<>();
        L.i("mytag", "loadDataerrand");
        //不能从网络中加载数据时(网络可以是连接的)或者网络未连接时
        if(!loadable || !isNetConnect ){
            mNearbyModularInfo = readData(mType);
            if(mNearbyModularInfo != null){
                showData(mNearbyModularInfo);
            } else{
                isNetConnect = NetUtil.isConnected(getmActivity());
                CollectionView.InventoryGroup<String, NearbyInfo> group1 =
                        inventory.newGroup(FORWARD_TASK);
                if(!isNetConnect) {
                    group1.setHeaderItem(getString(R.string.net_connect_error));
                } else {
                    group1.setHeaderItem(getString(R.string.error_server));
                }
                ////别忘了这步，更新视图
                mCollectionView.updateInventory(inventory);
                loadable = true;
            }
            return;
        }
        loadable = false;       //如果可以加载，则设置为不可加载
        setLoadable();          //过几分钟之后才能加载
        String endUrl = PropertiesUtil.getInstance().
                getProperty(AccessNetConst.NEARBYFRAGMENTINFOSENDPATH);
        Observable<NearbyModularInfo> observable = RetrofitServiceManager.getInstance()
                .create(NearbyBusiness.class).getNearbyFgInfos(endUrl,
                        mType).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).delaySubscription(0, TimeUnit.MILLISECONDS);
        addSubscription(observable.subscribe(new Subscriber<NearbyModularInfo>() {
            @Override
            public void onCompleted() {
//                view.setvisibililty(view.gone);  设置加载图片消失
                L.i("mytag", "complete123");
                L.i("mytag", "mNearbyModularInfo"+mNearbyModularInfo.toString());
                if (mNearbyModularInfo != null){
                    //保存到本地数据库中 start
                    saveData(mNearbyModularInfo);
                    //保存到本地数据库中 end
                    showData(mNearbyModularInfo);
                }
            }

            @Override
            public void onError(Throwable e) {
                L.i("mytag", "onError");
                loadable = false;
                loadData();
            }

            @Override
            public void onNext(NearbyModularInfo nearbyModularInfo) {
                mNearbyModularInfo = nearbyModularInfo;
                L.i("mytag", "onnext123");
            }
        }));

    }

    @Override
    public void setmType() {
        mType = SkillAndNeedConst.TYPE_ACCOMPANY;
    }


    @Override
    public void showData(NearbyModularInfo nearbyModularInfo) {
        NearbyInfo needNearbyInfo = new NearbyInfo();
        NearbyInfo skillNearbyInfo = new NearbyInfo();
        needNearbyInfo.setTag(FORWARD_TASK);
        needNearbyInfo.setTitle(mNearbyModularInfo.getNeedtitle());
        needNearbyInfo.setBody(mNearbyModularInfo.getNeedbody());
        needNearbyInfo.setImg(mNearbyModularInfo.getNeedimg());
        needNearbyInfo.setType(mNearbyModularInfo.getType());
        needNearbyInfo.setUpdatetime(mNearbyModularInfo.getNeedupdatetime());
        skillNearbyInfo.setTag(FORWARD_SKILL);
        skillNearbyInfo.setTitle(mNearbyModularInfo.getSkilltitle());
        skillNearbyInfo.setBody(mNearbyModularInfo.getSkillbody());
        skillNearbyInfo.setImg(mNearbyModularInfo.getSkillimg());
        skillNearbyInfo.setType(mNearbyModularInfo.getType());
        skillNearbyInfo.setUpdatetime(mNearbyModularInfo.getSkillupdatetime());
        CollectionView.InventoryGroup<String, NearbyInfo> group1 =
                inventory.newGroup(FORWARD_TASK);
        group1.setHeaderItem(getString(R.string.news_header_top_task));
        group1.addItem(needNearbyInfo);
        CollectionView.InventoryGroup<String, NearbyInfo> group2 =
                inventory.newGroup(FORWARD_SKILL);
        group2.setHeaderItem(getString(R.string.news_header_top_service));
        group2.addItem(skillNearbyInfo);
        ////别忘了这步，更新视图
        mCollectionView.updateInventory(inventory);
    }




}
