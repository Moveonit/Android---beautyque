package moveonit.beautyque.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import moveonit.beautyque.LoginActivity;
import moveonit.beautyque.response.RefreshResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
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
                            Response resp = call.execute();
                            if(resp != null){
                                if(resp.isSuccessful()) {
                                    SharedValue.setSharedPreferences(context, "token", ((RefreshResponse)resp.body()).getToken());
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
                            }else
                            {
                                callBackLogin(context);
                            }
                        }catch (IOException | NetworkOnMainThreadException ex)
                        {
                            callBackLogin(context);
                        }
                    }
                }
                else{
                    callBackLogin(context);
                }
                break;
            default:
                callBackLogin(context);
                break;
        }
        return blockingQueue;
    }


    public static void callBackLogin(final Context context){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (context, LoginActivity.class);
                context.startActivity(intent);
            }
        });



    }

}
