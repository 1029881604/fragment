package team.antelope.fg.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericliu.asyncexpandablelist.CollectionView;
import com.ericliu.asyncexpandablelist.CollectionViewCallbacks;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.NearbyModularInfoDaoImpl;
import team.antelope.fg.entity.NearbyInfo;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.ui.fragment.PhotographyFragment;
import team.antelope.fg.util.L;
import team.antelope.fg.util.NetUtil;


/**
 * @Author：hwc
 * @Date：2017/12/20 21:05
 * @Desc: ...
 */

public abstract class BaseNearbyFragment<T1, T2> extends BaseFragment implements CollectionViewCallbacks<T1, T2>  {
    public static final String TAG = "TAG2";
    public static final int FORWARD_TASK = 0;
    public double longitude;
    public double latitude;
    public static final int FORWARD_SKILL = 1;
    protected CollectionView<T1, T2> mCollectionView;
    protected CollectionView.Inventory<T1, T2> inventory;
    public CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected NearbyModularInfoDaoImpl modularInfoDao;
    public boolean loadable = true;
    protected boolean isNetConnect;
    protected String mType;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    /* ———————————— lazyload start ———————————— */

    /**
     * @Description 注意不是onCreateView()
     * @date 2018/1/3
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.i("TAGG", "onViewCreated");
        modularInfoDao = new NearbyModularInfoDaoImpl(getmActivity());
        setmType();
        isNetConnect = NetUtil.isConnected(getmActivity());
        isViewCreated = true;
        lazyLoad();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.i("TAGG", "setUserVisibleHint");
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }
    private void lazyLoad() {
        L.i("TAGG", "lazyLoad");
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            L.i("TAGG", "loadData");
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }
    //加载数据
    public abstract void loadData();
    //设置模块类型
    public abstract void setmType();

    public abstract void showData(NearbyModularInfo nearbyModularInfo);

    //将数据保存到本地
    public void saveData(NearbyModularInfo nearbyModularInfo) {
        NearbyModularInfo modularInfo = modularInfoDao.queryByType(mType);
        if(modularInfo == null){
            modularInfoDao.insert(nearbyModularInfo);
        }
    }
    //从本地读取数据
    public NearbyModularInfo readData(String type) {
        return modularInfoDao.queryByType(type);
    }
    // 延时任务，设置是否可以加载数据
    public void setLoadable(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadable = true;
                isNetConnect = NetUtil.isConnected(getmActivity());
            }
        }, 1000*60*2);
    }

    /* —————————————— lazyload end  ————————————*/
    @Override
    public RecyclerView.ViewHolder newCollectionHeaderView(Context context, int groupOrdinal, ViewGroup parent) {
        // Create a new view.
        View v = LayoutInflater.from(context)
                .inflate(R.layout.nearby_header_row_item, parent, false);

        return new TitleHolder(v);
    }


    @Override
    public void bindCollectionHeaderView(Context context, RecyclerView.ViewHolder holder, int groupOrdinal, T1 headerItem) {
        ((BaseNearbyFragment.TitleHolder) holder).getTextView().setText((String) headerItem);
    }

//    @Override
//    public void bindCollectionItemView(Context context, RecyclerView.ViewHolder holder, int groupOrdinal, T2 item) {
//        ObjectsItemHolder newsItemHolder = (ObjectsItemHolder) holder;
//        newsItemHolder.getTextViewTitle().setText(item.getNewsTitle());
//        newsItemHolder.getTextViewDescrption().setText(item.getNewsBody());
//    }
    /**
     * @Author hwc
     * @Date 2017/12/20
     * @TODO TitleHolder    titleviewHolder
     *
     */
    public static class TitleHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public TitleHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    /**
     * @Author hwc
     * @Date 2017/12/20
     * @TODO ObjectsItemHolder  承载ObjectsItem的viewHolder
     *
     */
    public static class ObjectsItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final ImageView iv_preview; // add by custom

        // add..........by custom
        public ObjectsItemHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.title);
            tvDescription = (TextView) v.findViewById(R.id.description);
            iv_preview = v.findViewById(R.id.iv_preview_nearby);
        }

        public TextView getTextViewTitle() {
            return tvTitle;
        }
        public TextView getTextViewDescrption() {
            return tvDescription;
        }
        public ImageView getIvPreview(){
            return iv_preview;
        }

    }
}
