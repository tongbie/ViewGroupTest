package com.example.bietong.viewgrouptest;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by BieTong on 2018/1/19.
 */

public class ToolClass {
    private static Context context;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public ToolClass(Context context){
        this.context=context;
        DisplayMetrics displayMetrics =context.getResources().getDisplayMetrics();
        SCREEN_WIDTH=displayMetrics.widthPixels;
        SCREEN_HEIGHT=displayMetrics.heightPixels;
    }

    public static float dp(float px){
        float scale = context.getResources().getDisplayMetrics().density;
        return px*scale+0.5f;
    }


}
