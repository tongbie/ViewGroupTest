package com.example.bietong.viewgrouptest;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by BieTong on 2018/1/18.
 */

public class SlipBack extends FrameLayout {
    private Activity activity = new Activity();
    private ViewGroup viewGroup;
    private View view;
    private float lastX;
    private float lastY;
    private float currentX;
    private float currentY;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private float SLIP;

    public SlipBack(Activity activity) {
        super(activity);
        this.activity = activity;
        this.setOnTouchListener(new OnTouch());
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SLIP = SCREEN_WIDTH / 10;
        {//替换原根布局
            viewGroup = (ViewGroup) activity.getWindow().getDecorView();//获取最顶层View
            view = viewGroup.getChildAt(0);//获取根LinearLayout
            viewGroup.removeView(view);
            this.addView(view);
            viewGroup.addView(this);
        }
    }

    /* 触摸事件 */
    class OnTouch implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentX = event.getRawX();
                    break;
                case MotionEvent.ACTION_UP:
                    if (currentX - lastX > SLIP) {
                        activity.finish();
                        activity.overridePendingTransition(0, R.anim.out_from_left);
                    }
                    break;
            }
            invalidate();
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getRawX();
                currentY = event.getRawY();
                float dx = currentX - lastX;
                float dy = currentY - lastY;
                if (Math.abs(dy) < Math.abs(dx) / 2) {
                    if (dx > SLIP) {
                        return true;
                    }
                }
        }
        return false;
    }
}


