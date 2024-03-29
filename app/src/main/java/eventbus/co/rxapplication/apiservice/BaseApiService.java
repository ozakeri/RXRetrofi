package eventbus.co.rxapplication.apiservice;

import java.util.List;

import eventbus.co.rxapplication.model.ResponseRepos;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("users/{username}/repos")
    Observable<List<ResponseRepos>> requestRepos(@Path("username") String userName);

}
