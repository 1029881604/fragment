package team.antelope.fg.ui.business;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.SkillPreInfo;

/**
 * @Author：hwc
 * @Date：2018/4/8 15:27
 * @Desc: ...
 */

public interface MeBusiness {
    // Retrofit单独使用时返回的是Call
    //Retrofit与Rxjava结合，将Call改成Observable
//    @GET("beforePath/{needAdd}/afterPath")

    @GET("{endPath}")
    Observable<Person> getUser(@Path("endPath") String endPath, @Query("id") long id);
}
