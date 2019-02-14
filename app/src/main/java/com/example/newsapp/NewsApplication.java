package com.example.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

}
