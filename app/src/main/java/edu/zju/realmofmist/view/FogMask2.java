package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import edu.zju.realmofmist.model.LocationModel;

/**
 * Created by desolate on 15/8/6.
 */
public class FogMask2 extends TextureView implements TextureView.SurfaceTextureListener {
    private Object mLock = new Object();
    boolean available = false;

    Paint paint, mPaint;

    List<LocationModel> locationStorage;
    GoogleMap map;

    public FogMask2(Context context) {
        super(context);
        init();
    }

    public FogMask2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setAlpha(0.9f);
        setOpaque(false);
        setBackgroundColor(Color.TRANSPARENT);
        setSurfaceTextureListener(this);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xddbbbbbb);
        paint.setAlpha(190);

        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        doDraw();
        available = true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void setLocationStorage(List<LocationModel> ls) {
        locationStorage = ls;
    }

    public void setMap(GoogleMap MAP) {
        map = MAP;
    }

    public void addPathCircle(Path path, LocationModel location) {

    }

    int j = 0;
    public void doDraw() {
        if (!available) {
            return ;
        }
        Surface surface = null;
        synchronized (mLock) {
            SurfaceTexture surfaceTexture = getSurfaceTexture();
            if (surfaceTexture == null) {
                Log.d("FogMask", "ST null on entry");
                return;
            }
            surface = new Surface(surfaceTexture);
        }
        Rect dirty = new Rect(0, getHeight(), getWidth(), getHeight());
        try {
            Canvas canvas = surface.lockCanvas(dirty);
            if (canvas == null) {
                surface.release();
                return;
            }
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

            Path path = new Path();
            int size = 0;
            if (locationStorage != null)
                size = locationStorage.size();
            if (size > 0 && map != null) {
                for (int i = 0; i < size; i++) {
                    android.graphics.Point point = map.getProjection().toScreenLocation(locationStorage.get(i).toLatLng());
                    path.addCircle(point.x, point.y, 40, Path.Direction.CW);
                }
            }
            canvas.drawPath(path, mPaint);

            try {
                surface.unlockCanvasAndPost(canvas);
            } catch (IllegalArgumentException iae) {
                Log.d("FogMask", "unlockCanvasAndPost failed: " + iae.getMessage());
//                        break;
            }
        }catch (Exception e) {

        }
    }

    public void doDrawPath() {
        if (!available) {
            return ;
        }
        Canvas canvas = lockCanvas();
        if (canvas == null) {
            return;
        }
        Path path = new Path();
        int size = 0;
        if (locationStorage != null)
            size = locationStorage.size();
        if (size > 0 && map != null) {
            Log.d("DoDraw", j + " 1");
            for (int i = 0; i < size; i++) {
                android.graphics.Point point = map.getProjection().toScreenLocation(locationStorage.get(i).toLatLng());
                Log.d("MYMAP", i+ " " +point.toString());
                path.addCircle(point.x, point.y, 40, Path.Direction.CW);
            }
        }
        canvas.drawPath(path, mPaint);
        unlockCanvasAndPost(canvas);
    }

    public void doDraw2() {
        Canvas canvas= lockCanvas();
        if (canvas == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffbbbbbb);
        paint.setAlpha(19);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        Paint mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
        Path path = new Path();

        int size = 0;
        if (locationStorage != null)
            size = locationStorage.size();

        if (size > 0 && map != null) {
            Log.d("DoDraw", (j) + " 2");
            for (int i = 0; i < size; i++) {
                android.graphics.Point point = map.getProjection().toScreenLocation(locationStorage.get(i).toLatLng());
                Log.d("MYMAP", i+ " " +point.toString());
                path.addCircle(point.x, point.y, 40, Path.Direction.CW);
            }
        }

        canvas.drawPath(path, mPaint);
        unlockCanvasAndPost(canvas);
    }
}
