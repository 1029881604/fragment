package team.antelope.fg.ui.business;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishSkill;

/**
 * Created by Kyrene on 2018/4/8.
 */

public interface CustmoizedBusiness {
    // Retrofit单独使用时返回的是Call
    //Retrofit与Rxjava结合，将Call改成Observable
//    @GET("beforePath/{needAdd}/afterPath")

    @GET("{endPath}")
    Observable<List<PublishSkill>> getList(@Path("endPath") String endPath, @Query("skilltype") String skilltype);

    @GET("{endPath}")
    Observable<Person> getPerson(@Path("endPath") String endPath, @Query("id") long id);

    @GET("{endPath}")
    Observable<List<PublishSkill>> getListByPerson(@Path("endPath") String endPath, @Query("id") long id);

    @GET("{endPath}")
    Observable<List<PublishSkill>> searchResult(@Path("endPath") String endPath, @Query("keyword") String keyword);

}


