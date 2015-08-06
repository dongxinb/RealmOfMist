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

/**
 * Created by desolate on 15/8/6.
 */
public class FogMask extends SurfaceView implements SurfaceHolder.Callback {


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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("FogMask", "onDraw()");
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(0xffbbbbbb);
//        paint.setAlpha(150);
//        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

//        Paint mPaint = new Paint();
//        mPaint.setAlpha(0);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mPaint.setAntiAlias(true);
//        canvas.drawCircle(10, 10, 40, mPaint);
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
