package com.test.sanjay.task.application;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by NEERAJ on 5/7/2017.
 */

public class Task extends Application {
    int screen_height=0, screen_width=0;
    WindowManager wm;
    DisplayMetrics displaymetrics;

    @Override
    public void onCreate() {
        super.onCreate();
        getdisplayheightWidth();
    }

    void getdisplayheightWidth() {
        wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        screen_height = displaymetrics.heightPixels;
        screen_width = displaymetrics.widthPixels;
    }

    public  int getScreen_height() {
        return screen_height;
    }

    public int getScreen_width() {
        return screen_width;
    }
}
