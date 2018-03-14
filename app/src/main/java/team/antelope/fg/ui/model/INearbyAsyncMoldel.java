package team.antelope.fg.ui.model;

import java.util.List;

import rx.Observable;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.SkillPreInfo;

/**
 * @Author：hwc
 * @Date：2017/12/27 11:16
 * @Desc: INearbyAsyncMoldel  数据访问接口
 */

public interface INearbyAsyncMoldel {
    //获取服务器端数据
    Observable <List<NeedPreInfo>> getServerNeedData(final String endUrl, String type,
                                                     double latitude, double longitude);
    //获取server data
    Observable<List<SkillPreInfo>> getServerSkillData(final String endUrl, String type,
                                                      double latitude, double longitude);
    //获取本地数据
    void getLocalNeedData();
    void getLocalSkillData();
    //设置本地数据
    void setLocalData(String title);
}
