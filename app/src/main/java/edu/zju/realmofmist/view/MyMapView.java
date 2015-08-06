package edu.zju.realmofmist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

//        Canvas canvas1 = new Canvas();
//
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(0xffbbbbbb);
//        paint.setAlpha(150);
//        //clearAwayPaint.setShader(new RadialGradient(100, 100, 100, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.MIRROR));
//
//        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
//
////        for (int i = 0; i < 10; i ++) {
////            Paint point = new Paint();
////            point.setStyle(Paint.Style.FILL);
////            point.setColor(Color.BLACK);
////            point.setAlpha(255);
////            point.setShader(new RadialGradient(100 * 25 * i, 100, 25, Color.TRANSPARENT, Color.TRANSPARENT, Shader.TileMode.MIRROR));
//////            canvas.drawCircle(100 + 25*i, 100, 25, point);
//////            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
////        }
////        Paint mPaint = new Paint();
////        mPaint.setAlpha(0);  //设置透明度为0
////        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //设置两图相交时的模式，那相交处同
////        mPaint.setAntiAlias(true);//抗锯齿
////        mPaint.setDither(true);//消除拉动，使画面圓滑
////        mPaint.setStyle(Paint.Style.STROKE); //设置画笔为空心，否则会是首尾连起来多边形内一块为透明。
////        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
////        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
////        mPaint.setStrokeWidth(20);//设置空心边框宽
//
//        Paint mPaint = new Paint();
//        mPaint.setAlpha(0);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mPaint.setAntiAlias(true);
//        canvas.drawCircle(10, 10, 40, mPaint);
////        Path path = new Path();
////        path.addCircle(100, 100, 40, Path.Direction.CW);
////        canvas.drawPath(path, mPaint);
//
//        Log.d("MapView", "onDraw()");
    }
}
