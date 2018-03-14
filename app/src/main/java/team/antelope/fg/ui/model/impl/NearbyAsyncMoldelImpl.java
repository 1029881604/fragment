package team.antelope.fg.ui.model.impl;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.SkillPreInfo;
import team.antelope.fg.ui.business.NearbyBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.ui.model.INearbyAsyncMoldel;
import team.antelope.fg.util.L;

/**
 * @Author：hwc
 * @Date：2017/12/27 11:21
 * @Desc: NearbyAsyncMoldelImpl  数据访问模型 实现类
 */

public class NearbyAsyncMoldelImpl implements INearbyAsyncMoldel {
    @Override
    public Observable<List<NeedPreInfo>> getServerNeedData(String endUrl, String type,
                                                  double latitude, double longitude) {
        L.i("tag", "NearbyAsyncMoldelImpl:getServerNeedData");
        return RetrofitServiceManager.getInstance()
                .create(NearbyBusiness.class).getNeedListInfo(endUrl, type, latitude, longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<SkillPreInfo>>  getServerSkillData(String endUrl, String type,
                                                              double latitude, double longitude) {
        L.i("tag", "NearbyAsyncMoldelImpl:getServerSkillData");
        return RetrofitServiceManager.getInstance()
                .create(NearbyBusiness.class).getSkillListInfo(endUrl, type, latitude, longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void getLocalNeedData() {

    }

    @Override
    public void getLocalSkillData() {

    }

    @Override
    public void setLocalData(String title) {

    }
}
