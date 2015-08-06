package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

/**
 * Created by desolate on 15/8/6.
 */
public class MyMapView extends MapView {

    public MyMapView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public MyMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
    }

    public MyMapView(Context context, GoogleMapOptions options) {
        super(context, options);
        setWillNotDraw(false);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Canvas canvas1 = new Canvas();
//
//
//    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Paint clearAwayPaint = new Paint();
        clearAwayPaint.setStyle(Paint.Style.FILL);
        clearAwayPaint.setColor(0xFFBBBBBB);
        clearAwayPaint.setAlpha(150);
        //clearAwayPaint.setShader(new RadialGradient(100, 100, 100, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.MIRROR));

        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), clearAwayPaint);
        Log.d("MapView", "onDraw()");
    }
}
