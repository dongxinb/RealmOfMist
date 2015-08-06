package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

/**
 * Created by desolate on 15/8/6.
 */
public class MyMapView extends MapView {

    public MyMapView(Context context) {
        super(context);
    }

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyMapView(Context context, GoogleMapOptions options) {
        super(context, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas canvas1 = new Canvas();

        Paint clearAwayPaint = new Paint();
        clearAwayPaint.setStyle(Paint.Style.FILL);
        clearAwayPaint.setColor(Color.BLACK);
        clearAwayPaint.setAlpha(255);
        clearAwayPaint.setShader(new RadialGradient(10, 10, 10, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.MIRROR));

        canvas.drawCircle(10, 10, 10, clearAwayPaint);
    }
}
