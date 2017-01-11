package moveonit.beautyque;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import moveonit.beautyque.rest.ApiCall;

public class MainActivityFragment extends CuddleFragment {
    MapView mMapView;
    private GoogleMap googleMap;
    private ListView listViewSpa;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        initTabHost();

        listViewSpa = (ListView) view.findViewById(R.id.listSpaView);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        ApiCall.initMainListSpa(getActivity(),listViewSpa);
        initmMap();

        return view;
    }

    private void initTabHost(){
        TabHost host = (TabHost) view.findViewById(R.id.maintabhost);
        host.setup();
        host.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Maps tab");
        spec.setContent(R.id.mapstab);
        spec.setIndicator("Maps");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Spa tab");
        spec.setContent(R.id.spatab);
        spec.setIndicator("Spas");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("News tab");
        spec.setContent(R.id.newstab);
        spec.setIndicator("News");
        host.addTab(spec);
    }

    private void initmMap(){
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;
                if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "You have to accept app's permission to enjoy all app's services!", Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
                ApiCall.initMapSpa(getActivity(),googleMap);

                if (mMap != null) {
                    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                        @Override
                        public void onMyLocationChange(Location arg0) {
                            // TODO Auto-generated method stub
                            //mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                            /*CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(arg0.getLatitude(),arg0.getLongitude())).zoom(15).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(arg0.getLatitude(),arg0.getLongitude())).zoom(14).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            mMap.setOnMyLocationChangeListener(null);
                        }
                    });
                }

            }
        });
    }
}
