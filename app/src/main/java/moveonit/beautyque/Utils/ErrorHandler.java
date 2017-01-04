package moveonit.beautyque.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import moveonit.beautyque.LoginActivity;
import moveonit.beautyque.response.RefreshResponse;
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

    public static BlockingQueue<Response> errorHandle(final Context context, int Code, String ErrorMessage, final Call mainCall) {
        final BlockingQueue<Response> blockingQueue = new ArrayBlockingQueue<>(1);
        switch (Code) {
            case 401:
                if(Objects.equals(ErrorMessage, "token_expired")) {
                    String token = SharedValue.getSharedPreferences(context, "token");
                    if (!Objects.equals(token, "")) {
                        Call<RefreshResponse> call = apiService.refreshToken("Bearer " + token, "V1");
                        try{
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            RefreshResponse resp = call.execute().body();
                            if(resp != null){
                                SharedValue.setSharedPreferences(context, "token", resp.getToken());
                                Call newCall = mainCall.clone();
                                try {
                                    blockingQueue.add(newCall.execute());
                                } catch (IOException ex) {
                                    callBackLogin(context);
                                }
                            }else
                            {
                                callBackLogin(context);
                            }
                        }catch (IOException ex)
                        {
                            callBackLogin(context);
                        }catch(NetworkOnMainThreadException ex)
                        {
                            callBackLogin(context);
                        }
                    }
                }
                break;
            default:
                callBackLogin(context);
                break;
        }
        return blockingQueue;
    }


    public static void callBackLogin(Context context){
        SharedValue.setSharedPreferences(context, "token", "");
        SharedValue.setSharedPreferences(context, "user", "");
        SharedValue.setSharedPreferences(context, "type", "");
        Intent i = new Intent(context, LoginActivity.class);

        context.startActivity(i);
    }
    /*public static Response errorHandle(final Context context, int Code, String ErrorMessage,final Class nextActivity) {
        switch (Code) {
            case 401:
                if(Objects.equals(ErrorMessage, "token_expired")) {
                    String token = SharedValue.getSharedPreferences(context, "token");
                    if (!Objects.equals(token, "")) {
                        Call<RefreshResponse> call = apiService.refreshToken("Bearer " + token, "V1");
                        call.enqueue(new Callback<RefreshResponse>() {
                            @Override
                            public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
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
                            public void onFailure(Call<RefreshResponse> call, Throwable t) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            break;
        }
        return false;
    }*/
}
