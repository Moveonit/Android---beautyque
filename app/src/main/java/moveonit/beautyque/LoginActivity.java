package moveonit.beautyque;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import moveonit.beautyque.response.ProvaResponse;
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
        setContentView(R.layout.login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        Button btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<ProvaResponse> call = apiService.getProva();
                call.enqueue(new Callback<ProvaResponse>() {
                    @Override
                    public void onResponse(Call<ProvaResponse>call, Response<ProvaResponse> response) {
                        String prova = response.body().getProva();

                        //Log.d("Test", "Number of movies received: " + prova);
                    }

                    @Override
                    public void onFailure(Call<ProvaResponse>call, Throwable t) {
                        // Log error here since request failed
                        //Log.e(TAG, t.toString());
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
