package com.glh.skindemo;

import android.app.Application;

import com.glh.skindemo.skin.LoadResources;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadResources.getInstance().init(this);
    }
}
