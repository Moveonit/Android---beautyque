package moveonit.beautyque.rest;

import moveonit.beautyque.model.Login;
import moveonit.beautyque.response.ProvaResponse;
import moveonit.beautyque.response.TokenResponse;
import moveonit.beautyque.response.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DANIELE on 14/11/2016.
 */

public interface ApiInterface {
    @GET("prova")
    Call<ProvaResponse> getProva();

    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") int id);

    @GET("users")
    Call<UserResponse> getUsers();

    @GET("me")
    Call<UserResponse> getMe(@Header("Authorization") String auth,
                             @Header("X-Api-Version") String version);

    @POST("login")
    Call<TokenResponse> getToken(@Body Login login);

    /*@GET("movie/{id}")
    Call<ProvaResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}