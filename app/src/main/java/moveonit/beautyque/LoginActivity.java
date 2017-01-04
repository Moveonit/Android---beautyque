package moveonit.beautyque;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.model.Login;
import moveonit.beautyque.model.User;
import moveonit.beautyque.response.TokenResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DANIELE on 09/11/2016.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.link_signup)
    TextView link_signup;

    final ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (validate()) {
                    login();
                    return;
                }
            }
        });

        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
    }

    public void login(){
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                getString(R.string.loginProgressDialog), true);
        Call<TokenResponse> call = apiService.getToken(new Login(_emailText.getText().toString(), _passwordText.getText().toString()));
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                //response = ApiClient.getResponse(getApplicationContext(),response,call);
                dialog.hide();
                dialog.dismiss();
                if(response.isSuccessful()) {
                    User user = response.body().getUser();
                    SharedValue.setSharedPreferences(getApplicationContext(), "token", response.body().getToken());
                    SharedValue.setSharedPreferences(getApplicationContext(), "user", user);
                    SharedValue.setSharedPreferences(getApplicationContext(), "type", user.getType());
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    getApplicationContext().startActivity(i);
                    finishAffinity();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                dialog.hide();

                ApiClient.getFailure(getApplicationContext(),t);
            }
        });
    }

    public void signUp(View v) {
        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(i);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.emailValidation));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3) {
            _passwordText.setError(getString(R.string.passwordValidation));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}