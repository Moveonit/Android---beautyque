package moveonit.beautyque.rest;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import moveonit.beautyque.LoginActivity;
import moveonit.beautyque.R;
import moveonit.beautyque.Utils.ErrorHandler;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class ApiClient {
    private static final String BASE_URL = "http://office.moveonit.it/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Response getResponse(Context context, Response response, Call call)
    {
        if (!response.isSuccessful()){
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                if(jObjError.getString("error") == "token_expired") {
                    BlockingQueue<Response> result = ErrorHandler.errorHandle(context, response.code(), jObjError.getString("error"), call);
                    try {
                        response = result.take();
                        return null;
                    } catch (InterruptedException ex) {
                        ErrorHandler.callBackLogin(context);
                    }
                }else{
                    ErrorHandler.callBackLogin(context);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                ErrorHandler.callBackLogin(context);
            }
        }
        return response;
    }

    public static void getFailure(Context context,Throwable t)
    {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

        if(context instanceof  LoginActivity)
        {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        }else
        {
            ErrorHandler.callBackLogin(context);

        }
    }
}
