package team.antelope.fg.ui.business;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import team.antelope.fg.entity.NearbyInfo;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.SkillPreInfo;

/**
 * @Author：hwc
 * @Date：2018/1/1 20:32
 * @Desc: ...附近业务
 */

public interface NearbyBusiness {

    // Retrofit单独使用时返回的是Call
    //Retrofit与Rxjava结合，将Call改成Observable
//    @GET("beforePath/{needAdd}/afterPath")
//    @GET("{endPath}") , 命名空间在这里设置
    @GET("nearby/{endPath}")
    Observable<List<NeedPreInfo>> getNeedListInfo(@Path("endPath") String endPath,
                                                  @Query("type") String type,
                                                  @Query("latitude") double latitude,
                                                  @Query("longitude") double longitude);

    @GET("nearby/{endSkillPath}")
    Observable<List<SkillPreInfo>> getSkillListInfo(@Path("endSkillPath") String endPath,
                                                    @Query("type") String type,
                                                    @Query("latitude") double latitude,
                                                    @Query("longitude") double longitude);

    @GET("nearby/{endPath}")
    Observable<PublishNeed> getNearbyPublishNeed(@Path("endPath") String endPath, @Query("id") long id);

    @GET("nearby/{endPath}")
    Observable<PublishSkill> getNearbyPublishSkill(@Path("endPath") String endPath, @Query("id") long id);

    @GET("nearby/{endPath}")
    Observable<NearbyModularInfo> getNearbyFgInfos(@Path("endPath") String endPath, @Query("type") String type);

}
