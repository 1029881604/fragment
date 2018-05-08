package team.antelope.fg.ui.presenter.impl;

import android.app.Dialog;

import java.util.List;
import java.util.Properties;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.FgApp;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.SkillPreInfo;
import team.antelope.fg.ui.model.INearbyAsyncMoldel;
import team.antelope.fg.ui.model.impl.NearbyAsyncMoldelImpl;
import team.antelope.fg.ui.presenter.INearbyAsyncPresenter;
import team.antelope.fg.ui.view.INearbyAsyncView;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：hwc
 * @Date：2017/12/27 11:13
 * @Desc: NearbyAsyncPersenterImpl  异步加载代理
 */

public class NearbyAsyncPersenterImpl implements INearbyAsyncPresenter {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private INearbyAsyncView mView;
    private INearbyAsyncMoldel mMoldel;
    private Properties mProp;
    private FgApp mApp;

    public NearbyAsyncPersenterImpl(INearbyAsyncView mView) {
        this.mView = mView;
        this.mMoldel = new NearbyAsyncMoldelImpl();
        mProp = PropertiesUtil.getInstance();
        mApp = FgApp.getInstance();
    }


    @Override
    public void getServerNeedData(String type, double latitude, double longitude) {
        L.i("tag", "persenter:getServerNeedData");
        String endUrl = mProp.getProperty(AccessNetConst.GETNEEDINFOENDPATH);
        final Dialog dialog = mView.showProgressDialog("正在加载...");//显示对话框
        addSubscription( mMoldel.getServerNeedData(endUrl, type,
                latitude, longitude).subscribe(new Subscriber<List<NeedPreInfo>>() {
            @Override
            public void onCompleted() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                L.i("tag", "onCompleted:onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                L.i("tag", "onError:onError");
//                mView.showToast("获取数据失败");
                mView.showDataError();
                L.i("whoami", "getServerNeedData");
            }

            @Override
            public void onNext(List<NeedPreInfo> needs) {
                for (int i = 0; i < needs.size(); i++) {
                    L.i("tag", needs.get(i).toString());
                }
                L.i("tag", "onNext:onNext");
                mView.showNeedListData(needs);
            }
        }));

        L.i("tag", "persenter:getServerNeedData2");

    }

    @Override
    public void getLocalNeedData() {

    }

    @Override
    public void getServerSkillData(String type, double latitude, double longitude) {
        L.i("tag", "persenter:getServerSkillData");
        String endUrl = mProp.getProperty(AccessNetConst.GETSKILLINFOENDPATH);
        L.i("tag", endUrl);
        L.i("tag", mProp.getProperty(AccessNetConst.BASEPATH)+endUrl);
        final Dialog dialog = mView.showProgressDialog("正在加载...");//显示对话框
        Observable<List<SkillPreInfo>> stringObservable = mMoldel.getServerSkillData(endUrl,
                type, latitude, longitude);
        // addSubscription 管理订阅
        addSubscription(stringObservable.subscribe(new Subscriber<List<SkillPreInfo>>() {
            @Override
            public void onCompleted() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                L.i("tag", "onCompleted:onCompleted");
            }

            @Override
            public void onError(Throwable e) {

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                L.i("tag", "onError:onError");
//                mView.showToast("获取数据失败");
                mView.showDataError();
                L.i("whoami", "getServerSkillData");
            }

            @Override
            public void onNext(List<SkillPreInfo> skills) {

                for (int i = 0; i < skills.size(); i++) {
                    L.i("tag", skills.get(i).toString());
                }
                L.i("tag", "onNext:onNext");
                mView.showSkillListData(skills);
            }
        }));
    }

    @Override
    public void getLocalSkillData() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (compositeSubscription.hasSubscriptions()) {
            if (!compositeSubscription.isUnsubscribed()) {
                compositeSubscription.unsubscribe();
                compositeSubscription.clear();
            }
        }
    }

}
