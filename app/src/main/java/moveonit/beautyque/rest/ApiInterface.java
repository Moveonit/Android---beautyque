package moveonit.beautyque.rest;

import moveonit.beautyque.response.ProvaResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DANIELE on 14/11/2016.
 */

public interface ApiInterface {
    @GET("prova")
    Call<ProvaResponse> getProva();

    /*@GET("movie/{id}")
    Call<ProvaResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}