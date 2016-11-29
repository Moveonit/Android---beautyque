package moveonit.beautyque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import moveonit.beautyque.Utils.ErrorHandler;
import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.response.UserResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = SharedValue.getSharedPreferences(getApplicationContext(), "token");
        if (!Objects.equals(token, "")) {
            Call<UserResponse> call = apiService.getMe("Bearer " + token, "V1");
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        getApplicationContext().startActivity(i);
                        finishAffinity();
                        return;
                        //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle other responses
                        try {
                            setContentView(R.layout.splashscreen);

                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            ErrorHandler.errorHandle(getApplicationContext(), response.code(), jObjError.getString("error"), MainActivity.class);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    SplashScreen.this.startActivity(mainIntent);
                                    SplashScreen.this.finish();
                                }
                            }, SPLASH_DISPLAY_LENGTH);
                            // Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {


        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
            setContentView(R.layout.splashscreen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}