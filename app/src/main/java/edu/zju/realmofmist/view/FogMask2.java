package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by desolate on 15/8/6.
 */
public class FogMask2 extends TextureView implements TextureView.SurfaceTextureListener {
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

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        doDraw();
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

    public void doDraw() {
        Canvas canvas= lockCanvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffbbbbbb);
        paint.setAlpha(190);
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

        unlockCanvasAndPost(canvas);
    }
}
