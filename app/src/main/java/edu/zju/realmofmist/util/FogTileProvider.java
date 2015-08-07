package edu.zju.realmofmist.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.util.List;

import edu.zju.realmofmist.model.LocationModel;

/**
 * Created by desolate on 15/8/7.
 */
public class FogTileProvider implements TileProvider {
    GoogleMap mMap;
    Paint mKeepPaint, mClearPaint;
    List<LocationModel> mLocationStorage = null;
//    Bitmap mMask;

    public FogTileProvider(GoogleMap map) {
        this.mMap = map;

        mKeepPaint = new Paint();
        mKeepPaint.setStyle(Paint.Style.FILL);
        mKeepPaint.setColor(0xddbbbbbb);
        mKeepPaint.setAlpha(190);

        mClearPaint = new Paint();
        mClearPaint.setAlpha(0);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mClearPaint.setAntiAlias(true);
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        Log.d("FogTileProvider", String.format("%d %d %d", x, y, zoom));
        Bitmap mMask = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMask);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mKeepPaint);

        LatLngBounds bounds = boundsOfTile(x, y, zoom);
        double thisTileWidth = bounds.northeast.longitude - bounds.southwest.longitude;
        double thisTileHeight = bounds.northeast.latitude - bounds.southwest.latitude;
        float radius = (float) (0.0004 * (float) 256 / thisTileWidth);
//        canvas.drawCircle(100, 100, 20, mClearPaint);
        if (mLocationStorage != null) {
            Path path = new Path();
            for (LocationModel location: mLocationStorage) {
                if (location.getLongitude() < bounds.northeast.longitude + 0.01 && location.getLongitude() > bounds.southwest.longitude - 0.01 &&
                        location.getLatitude() < bounds.northeast.latitude + 0.01 && location.getLatitude() > bounds.southwest.latitude - 0.01) {
                    path.addCircle((float)(((location.getLongitude() - bounds.southwest.longitude) / thisTileWidth) * 256),
                            (float)(256 - (((location.getLatitude() - bounds.southwest.latitude) / thisTileHeight) * 256)), radius, Path.Direction.CW);
                }

            }
            canvas.drawPath(path, mClearPaint);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mMask.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Tile tile = new Tile(256, 256, stream.toByteArray());
        return tile;
//        return null;
    }

    public void setLocationStorage(List<LocationModel> locationStorage) {
        this.mLocationStorage = locationStorage;
    }

    private LatLngBounds boundsOfTile(int x, int y, int zoom) {
        int noTiles = (1 << zoom);
        double longitudeSpan = 360.0 / noTiles;
        double longitudeMin = -180.0 + x * longitudeSpan;

        double mercatorMax = 180 - (((double) y) / noTiles) * 360;
        double mercatorMin = 180 - (((double) y + 1) / noTiles) * 360;
        double latitudeMax = toLatitude(mercatorMax);
        double latitudeMin = toLatitude(mercatorMin);

        LatLngBounds bounds = new LatLngBounds(new LatLng(latitudeMin, longitudeMin), new LatLng(latitudeMax, longitudeMin + longitudeSpan));
        return bounds;
    }

    public static double toLatitude(double y) {
        double radians = Math.atan(Math.exp(Math.toRadians(y)));
        return Math.toDegrees(2 * radians)-90;
    }
}
