package team.antelope.fg.ui.business;

import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Author：hwc
 * @Date：2018/5/10 10:44
 * @Desc: ... 用户业务接口   服务器返回的json必须正确
 */

public interface UserBusiness {
    @POST("user/{endPath}")//只有在{endPath}之前的才会被认为命名空间？
    Observable<String> logout(@Path("endPath") String endPath);
}
