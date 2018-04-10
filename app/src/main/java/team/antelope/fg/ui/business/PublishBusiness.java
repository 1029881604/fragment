package team.antelope.fg.ui.business;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PersonNeed;
import team.antelope.fg.entity.PersonSkill;

/**
 * @Author：hwc
 * @Date：2018/4/8 15:27
 * @Desc: ...
 */

public interface PublishBusiness {

    @GET("{endPath}")
    Observable<List<PersonSkill>> getAllPersonSkill(@Path("endPath") String endPath);
    @GET("{endPath}")
    Observable<List<PersonNeed>> getAllPersonNeed(@Path("endPath") String endPath);
}
