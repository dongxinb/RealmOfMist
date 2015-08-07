package edu.zju.realmofmist.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.text.DateFormat;
import java.util.Date;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.util.FogTileProvider;
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
//    FogMask2 mMaskView;

    public static float MistSize = 60000f;
    public static float ImageSize = 637 * 2f;

    private LocationStorageModel mLocationStorage;

    private MyMapView mMapView;
    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap;

    private boolean mRequestingLocationUpdates = true;
    private boolean mMoveToCurPos = false;

    private LocationModel mCurrentLocation;
    private LocationRequest mLocationRequest;

    private Bitmap mMistBitmap;
    private Paint mPaint;
    private GroundOverlay mImageOverlay;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private FogTileProvider mTileProvider;
    private TileOverlay mTileoverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MyDebug", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkView();
        setMapView();

//        addPreDrawListener();

        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume();
        mMapView.getMapAsync(this);

        mLocationStorage = new LocationStorageModel();
        buildGoogleApiClient();
        createLocationRequest();

//        setMistBitmap();

        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onStart() {
        Log.d("MyDebug", "OnStart");
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        Log.d("MyDebug", "OnStop");
        super.onStop();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        Log.d("MyDebug", "OnPause");
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        Log.d("MyDebug", "OnResume");
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onDestroy() {
        Log.d("MyDebug", "OnDestroy");
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory () {
        Log.d("MyDebug", "OnLowMemory");
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        Log.d("MyDebug", "OnSave");
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private void linkView() {
        mMenu = (FloatingActionsMenu)findViewById(R.id.menu_actions);
        mMenuProfile = (FloatingActionButton)findViewById(R.id.menu_profile);
        mMenuRanking = (FloatingActionButton)findViewById(R.id.menu_ranking);
        mMenuLogin = (FloatingActionButton)findViewById(R.id.menu_login);
//        mMaskView = (FogMask2)findViewById(R.id.mask_view);

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
        Log.d("OnPreDraw", "onPreDraw()");
        CameraPosition position = mMap.getCameraPosition();
        Log.d("Camera", String.format("%f %s", position.zoom, position.target.toString()));
//        mMaskView.setMap(mMap);
//        mMaskView.doDraw();
//        mMaskView.doDrawPath();
//        System.out.println(mMap.getCameraPosition());
        return true;
    }

    // set up fragment
    private void setMapView() {
        mMapView = (MyMapView) findViewById(R.id.mapView);

    }

    private void setMistBitmap() {
        Bitmap mistOrign = BitmapFactory.decodeResource(getResources(), R.drawable.mist);
        mMistBitmap = mistOrign.copy(Bitmap.Config.ARGB_8888, true);

        LatLng Singapore = new LatLng(1.358557, 103.838171);
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromBitmap(mMistBitmap))
                .position(Singapore, MistSize, MistSize);
        mImageOverlay = mMap.addGroundOverlay(newarkMap);
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
//        mMaskView.setLocationStorage(mLocationStorage.getLocationList());

        // move to current location when starting
        if (mMap != null && mMoveToCurPos == false) {
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(cameraUpdate);
            mMoveToCurPos = true;
        }
//        if (mMap != null) {
//            mapProcess();
//            mImageOverlay.setImage(BitmapDescriptorFactory.fromBitmap(mMistBitmap));
//        }
        Log.d("Locations", mLocationStorage.getLocation(mLocationStorage.getSize()-1).toString());

        if (mTileProvider != null) {
            mTileProvider.setLocationStorage(mLocationStorage.getLocationList());
            mTileoverlay.clearTileCache();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        mMap = googleMap;
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);

        //        LatLng Singapore = new LatLng(1.358557, 103.838171);

//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.mist))
//                .position(Singapore, 60000f, 45000f);
//        mMap.addGroundOverlay(newarkMap);

        mTileProvider = new FogTileProvider(mMap);
        mTileProvider.setLocationStorage(mLocationStorage.getLocationList());
        TileOverlayOptions opts = new TileOverlayOptions();
        opts.fadeIn(true);
        opts.tileProvider(mTileProvider);
        mTileoverlay = mMap.addTileOverlay(opts);

    }

    private void mapProcess() {
        Canvas canvas = new Canvas(mMistBitmap);
        canvas.drawCircle(-distLng(mCurrentLocation.getLongitude(), 103.838171) / MistSize * ImageSize + ImageSize / 2, distLat(mCurrentLocation.getLatitude(), 1.358557) / MistSize * ImageSize + ImageSize / 2, 1, mPaint);
    }

    private float distLat(double lat1, double lat2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist * (float)(dLat / Math.abs(dLat));
    }

    private float distLng(double lng1, double lng2) {
        double earthRadius = 6371000; //meters
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist * (float)(dLng / Math.abs(dLng));
    }


}
