package eventbus.co.rxapplication.apiservice;

public class UtilsApi {

    private static final String BASE_URl = "https://api.github.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getRetrofit(BASE_URl).create(BaseApiService.class);
    }
}
