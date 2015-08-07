package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import edu.zju.realmofmist.model.LocationModel;

/**
 * Created by desolate on 15/8/6.
 */
public class FogMask extends SurfaceView implements SurfaceHolder.Callback {

    Paint paint, mPaint;
    List<LocationModel> locationStorage;
    GoogleMap map;

    public FogMask(Context context) {
        super(context);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.d("FogMask", "context");
    }

    public FogMask(Context context, AttributeSet attrs) {
        super(context, attrs);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
        setFocusable(true);
        Log.d("FogMask", "context attr");
        setWillNotDraw(false);

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
    public void surfaceCreated(SurfaceHolder holder) {
        doDraw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setMap(GoogleMap MAP) {
        map = MAP;
    }

    public void setLocationStorage(List<LocationModel> ls) {
        locationStorage = ls;
    }

    int j = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("FogMask", "onDraw()");
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
    }

    private void doDraw() {
        Canvas canvas=getHolder().lockCanvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffbbbbbb);
        paint.setAlpha(150);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        Paint mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAntiAlias(true);
        Path path = new Path();
        for (int i = 0; i < 100; i ++) {
            path.addCircle(100+i*2, 100 + i, 20, Path.Direction.CW);
        }
        for (int i = 0; i < 100; i ++) {
            path.addCircle(400+i*2, 200 + i * 3, 10, Path.Direction.CW);
        }
//        path.moveTo(100, 100);
//        path.arcTo(100, 100, 200, 100, (float)Math.PI / 6, (float)Math.PI * 5/ 6, true);
        path.addCircle(300, 300, 40, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        getHolder().unlockCanvasAndPost(canvas);
        Log.d("FogMask", "doDraw()");
        invalidate();
    }
}
