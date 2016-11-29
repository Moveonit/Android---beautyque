package moveonit.beautyque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.model.User;
import moveonit.beautyque.response.ProvaResponse;
import moveonit.beautyque.response.UserResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserCustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        final ListView lista = (ListView) findViewById(R.id.users_list);



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = SharedValue.getSharedPreferences(getApplicationContext(), "token");

        Call<UserResponse> call = apiService.getUsers("Bearer " + token, "V1");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse>call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    List<User> utenti = response.body().getResults();
                    adapter = new UserCustomAdapter(getApplicationContext(), R.layout.rowcustom, utenti);
                    lista.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                // Log error here since request failed
                //Log.e(TAG, t.toString());
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
        if (id == R.id.logout_settings) {
            SharedValue.setSharedPreferences(getApplicationContext(),"token","");
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplicationContext().startActivity(i);
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
