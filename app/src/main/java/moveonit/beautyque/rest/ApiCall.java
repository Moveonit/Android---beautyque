package moveonit.beautyque.rest;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import moveonit.beautyque.Adapter.CustomListSpaAdapter;
import moveonit.beautyque.R;
import moveonit.beautyque.Utils.SharedValue;
import moveonit.beautyque.model.Spa;
import moveonit.beautyque.response.SpasResponse;
import moveonit.beautyque.response.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DANIELE on 09/01/2017.
 */

public class ApiCall {

    public static void initMain(final Context context, final TextView nameMenu, final TextView emailMenu, final NavigationView navigationView) {
        String token = SharedValue.getSharedPreferences(context, "token");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiService.getMe("Bearer " + token, "V1");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse>call, Response<UserResponse> response) {
                response = ApiClient.getResponse(context,response,call);
                emailMenu.setText(response.body().getData().get(0).getEmail());
                switch (response.body().getData().get(0).getType()) {
                    case "Employee":
                        nameMenu.setText(response.body().getData().get(0).getEmployee().getName());
                        navigationView.getMenu().findItem(R.id.nav_employees).setVisible(false);
                        break;
                    case "Spa":
                        nameMenu.setText(response.body().getData().get(0).getSpa().getCompanyName());
                        navigationView.getMenu().findItem(R.id.nav_employees).setVisible(true);
                        break;
                    case "Guest":
                        nameMenu.setText(response.body().getData().get(0).getGuest().getName() + response.body().getData().get(0).getGuest().getSurname());
                        navigationView.getMenu().findItem(R.id.nav_employees).setVisible(false);
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                ApiClient.getFailure(context,t);
            }
        });
    }

    public static void initMapSpa(final Context context,final GoogleMap googleMap){
        String token = SharedValue.getSharedPreferences(context, "token");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SpasResponse> call = apiService.getSpas("Bearer " + token, "V1");
        call.enqueue(new Callback<SpasResponse>() {
            @Override
            public void onResponse(Call<SpasResponse>call, Response<SpasResponse> response) {
                response = ApiClient.getResponse(context,response,call);
                List<Spa> spas = response.body().getData();
                for (Spa spa:spas) {
                    LatLng point = new LatLng(spa.getLatitude(),spa.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(point).title(spa.getCompanyName()).snippet(spa.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_spa_black_48dp)));
                }
            }

            @Override
            public void onFailure(Call<SpasResponse>call, Throwable t) {
                ApiClient.getFailure(context,t);
            }
        });
    }

    public static void initMainListSpa(final Context context, final ListView listViewSpa){
        String token = SharedValue.getSharedPreferences(context, "token");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<SpasResponse> call = apiService.getSpas("Bearer " + token, "V1");
        call.enqueue(new Callback<SpasResponse>() {
            @Override
            public void onResponse(Call<SpasResponse>call, Response<SpasResponse> response) {
                response = ApiClient.getResponse(context,response,call);
                final List<Spa> spas = response.body().getData();
                CustomListSpaAdapter adapter;
                adapter = new CustomListSpaAdapter(context, R.layout.custom_row_list_spas, spas);
                listViewSpa.setAdapter(adapter);

                listViewSpa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Toast.makeText(context, spas.get(position).getCompanyName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<SpasResponse>call, Throwable t) {
                ApiClient.getFailure(context,t);
            }
        });

    }
}
