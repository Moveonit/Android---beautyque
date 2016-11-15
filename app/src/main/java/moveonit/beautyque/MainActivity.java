package moveonit.beautyque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.response.ProvaResponse;
import moveonit.beautyque.rest.ApiClient;
import moveonit.beautyque.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        final TextView text = (TextView) findViewById(R.id.textMain);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProvaResponse> call = apiService.getProva();
        call.enqueue(new Callback<ProvaResponse>() {
            @Override
            public void onResponse(Call<ProvaResponse>call, Response<ProvaResponse> response) {
                String prova = response.body().getProva();
                text.setText(prova);
                Log.d("Test", "Number of movies received: " + prova);
            }

            @Override
            public void onFailure(Call<ProvaResponse>call, Throwable t) {
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
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            getApplicationContext().startActivity(i);
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
