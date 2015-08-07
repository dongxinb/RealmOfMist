package edu.zju.realmofmist.app;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

/**
 * Created by desolate on 15/8/7.
 */
public class MyApplication extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application", "onCreate()");
        ActiveAndroid.initialize(this);
    }
}
