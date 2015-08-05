package edu.zju.realmofmist.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * Created by desolate on 15/8/6.
 */
public class FogMask extends Surface implements SurfaceHolder.Callback {


    public FogMask(SurfaceTexture surfaceTexture) {
        super(surfaceTexture);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
