package moveonit.beautyque.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import moveonit.beautyque.response.TokenResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DANIELE on 16/11/2016.
 */

public class ErrorHandler {

    final static ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    public static boolean errorHandle(final Context context, int Code, String ErrorMessage,final Class nextActivity) {
        switch (Code) {
            case 401:
                if(Objects.equals(ErrorMessage, "token_expired")) {
                    String token = SharedValue.getSharedPreferences(context, "token");
                    if (!Objects.equals(token, "")) {
                        Call<TokenResponse> call = apiService.refreshToken("Bearer " + token, "V1");
                        call.enqueue(new Callback<TokenResponse>() {
                            @Override
                            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                if (response.isSuccessful()) {
                                    SharedValue.setSharedPreferences(context,"token",response.body().getToken());
                                    Toast.makeText(context, "refreshed", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(context, nextActivity);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    context.startActivity(i);

                                    //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle other responses
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                                        Toast.makeText(context, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenResponse> call, Throwable t) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            break;
        }
        return false;
    }
}
