package ruhulbdapp.com.blogspot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitClient {
 
    @GET("continents")
    Call<OurMainDataClass> getData(@Query("api_token") String token);

}
