package edu.zju.realmofmist.app;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.view.FogMask2;
import edu.zju.realmofmist.model.LocationModel;
import edu.zju.realmofmist.model.LocationStorageModel;
import edu.zju.realmofmist.view.MyMapView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ViewTreeObserver.OnPreDrawListener {

    FloatingActionsMenu mMenu;
    FloatingActionButton mMenuProfile;
    FloatingActionButton mMenuRanking;
    FloatingActionButton mMenuLogin;
    FogMask2 mMaskView;

    private LocationStorageModel mLocationStorage;

    private MyMapView mMapView;
    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap;

    private boolean mRequestingLocationUpdates = true;
    private boolean mMoveToCurPos = false;

    private LocationModel mCurrentLocation;
    private String mLastUpdateTime;
    private LocationRequest mLocationRequest;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkView();
        setMapView();

        addPreDrawListener();

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        mLocationStorage = new LocationStorageModel();
        buildGoogleApiClient();
        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    private void linkView() {
        mMenu = (FloatingActionsMenu)findViewById(R.id.menu_actions);
        mMenuProfile = (FloatingActionButton)findViewById(R.id.menu_profile);
        mMenuRanking = (FloatingActionButton)findViewById(R.id.menu_ranking);
        mMenuLogin = (FloatingActionButton)findViewById(R.id.menu_login);
        mMaskView = (FogMask2)findViewById(R.id.mask_view);

        View.OnClickListener menuItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mMenuProfile) {
                    //Should change to PorfileActivity
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivityForResult(intent, 1000);
                    Log.d("Main", "Profile menu pressed.");
                }else if (v == mMenuRanking) {
                    Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                    startActivityForResult(intent, 1000);
                    Log.d("Main", "Ranking menu pressed.");
                }
                else if (v == mMenuLogin) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1000);
                    Log.d("Main", "Login menu pressed.");
                }
                mMenu.toggle();
            }
        };
        mMenuProfile.setOnClickListener(menuItemListener);
        mMenuRanking.setOnClickListener(menuItemListener);
        mMenuLogin.setOnClickListener(menuItemListener);
    }

    private void addPreDrawListener() {
        ViewTreeObserver observer = mMapView.getViewTreeObserver();
        observer.addOnPreDrawListener(this);

    }

    //pre draw listener
    @Override
    public boolean onPreDraw() {
//        Log.d("OnPreDraw", "onPreDraw()");
        CameraPosition position = mMap.getCameraPosition();
        Log.d("Camera", String.format("%f %s", position.zoom, position.target.toString()));
        mMaskView.doDraw();
//        System.out.println(mMap.getCameraPosition());
        return true;
    }

    // set up fragment
    private void setMapView() {
        mMapView = (MyMapView) findViewById(R.id.mapView);

    }

    // set up google play service
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // init location request
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
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

    @Override
    public void onConnected(Bundle bundle) {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        mCurrentLocation = new LocationModel(currentLatitude, currentLongitude, DateFormat.getTimeInstance().format(new Date()));

        mLocationStorage.insertLocation(mCurrentLocation);
        mMaskView.setLocationStorage(mLocationStorage.getLocationList());

        // move to current location when starting
        if (mMap != null && mMoveToCurPos == false) {
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(cameraUpdate);
            mMoveToCurPos = true;
        }
//        if (mMap != null)
//            Log.d("MYMAP", mMap.getCameraPosition().toString() + " " + mMap.getMaxZoomLevel() + " " + mMap.getMinZoomLevel());
        Log.d("Locations", mLocationStorage.getLocation(mLocationStorage.getSize()-1).toString());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        mMap = googleMap;
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMaskView.setMap(mMap);
    }
}
