package moveonit.beautyque.rest;

import moveonit.beautyque.model.Login;
import moveonit.beautyque.model.SignupUser;
import moveonit.beautyque.response.ProvaResponse;
import moveonit.beautyque.response.RefreshResponse;
import moveonit.beautyque.response.SpasResponse;
import moveonit.beautyque.response.TokenResponse;
import moveonit.beautyque.response.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by DANIELE on 14/11/2016.
 */

public interface ApiInterface {
    @GET("prova")
    Call<ProvaResponse> getProva();

    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") int id);

    @GET("users")
    Call<UserResponse> getUsers(@Header("Authorization") String auth,
                                @Header("X-Api-Version") String version);

    @GET("spas")
    Call<SpasResponse> getSpas(@Header("Authorization") String auth,
                                @Header("X-Api-Version") String version);

    @GET("me")
    Call<UserResponse> getMe(@Header("Authorization") String auth,
                             @Header("X-Api-Version") String version);

    @GET("refresh")
    Call<RefreshResponse> refreshToken(@Header("Authorization") String auth,
                                       @Header("X-Api-Version") String version);

    @POST("login")
    Call<TokenResponse> getToken(@Body Login login);

    @POST("signup")
    Call<TokenResponse>  signup(@Body SignupUser newUser);

    @GET("checkemail/{email}")
    Call<TokenResponse> checkEmail(@Path("email") String email);
}