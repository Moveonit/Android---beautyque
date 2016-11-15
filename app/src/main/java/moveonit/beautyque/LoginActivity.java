package moveonit.beautyque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.model.Login;
import moveonit.beautyque.response.TokenResponse;
import moveonit.beautyque.response.UserResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DANIELE on 09/11/2016.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        getApplicationContext().startActivity(i);
                        finishAffinity();
                        //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle other responses
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            setContentView(R.layout.login_activity);
            Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
            setSupportActionBar(toolbar);


            final EditText email = (EditText) findViewById(R.id.email);
            final EditText password = (EditText) findViewById(R.id.password);

            Button btn_login = (Button) findViewById(R.id.btn_login);

            btn_login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                    Call<TokenResponse> call = apiService.getToken(new Login(email.getText().toString(), password.getText().toString()));
                    call.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("Login", response.message());
                                SharedValue.setSharedPreferences(getApplicationContext(), "token", response.body().getToken());
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                getApplicationContext().startActivity(i);
                                finishAffinity();
                                //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle other responses
                                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}

    /*ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UserResponse> call = apiService.getUsers();
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse>call, Response<UserResponse> response) {
                        //String prova = response.body().getEmail();
                        List<User> users = response.body().getResults();
                        //Log.d("Test", "Number of movies received: " + users);
                    }

                    @Override
                    public void onFailure(Call<UserResponse>call, Throwable t) {
                        // Log error here since request failed
                        //Log.e(TAG, t.toString());
                    }
                });*/
