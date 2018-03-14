package team.antelope.fg.ui.presenter;

import rx.Subscription;

/**
 * @Author：hwc
 * @Date：2017/12/27 10:38
 * @Desc: 异步加载数据代理接口
 */

public interface INearbyAsyncPresenter {
    void getServerNeedData(String type, double latitude, double longitude);
    void getLocalNeedData();
    void getServerSkillData(String type, double latitude, double longitude);
    void getLocalSkillData();
    void onDestroy();
    /**
     * @Description 订阅管理
     * @date 2018/1/3
     */
    void addSubscription(Subscription subscription);
    /**
     * @Description 取消订阅   一般在view(实际上是activity or fragment)中调用
     * @date 2018/1/3
     */
    void unSubscribe();
}
