package com.example.bietong.viewgrouptest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by BieTong on 2018/1/18.
 */

public class RightSlip extends FrameLayout {
    private Activity activity=new Activity();
    private ViewGroup viewGroup;
    private View view;
    private Paint paint = new Paint();
    private int currentX;//当前横坐标
    private boolean isSlide = false;//判断是否允许滑动
    private final int SLIPLEFT = 0x000;
    private final int SLIPRIGHT = 0x001;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public RightSlip(Activity activity) {
        super(activity);
        this.activity=activity;
        this.setBackgroundColor(getResources().getColor(R.color.translucent_background));
        viewGroup = (ViewGroup) activity.getWindow().getDecorView();//获取最顶层View
        view = viewGroup.getChildAt(0);//获取根LinearLayout
        this.setOnTouchListener(new OnClick());
        DisplayMetrics displayMetrics =getContext().getResources().getDisplayMetrics();
        SCREEN_WIDTH=displayMetrics.widthPixels;
        SCREEN_HEIGHT=displayMetrics.heightPixels;
    }

    /* 触摸事件 */
    class OnClick implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int eventAction = event.getAction();
            switch (eventAction) {
                case MotionEvent.ACTION_DOWN:
                    currentX = (int) event.getX();
                    if (currentX > dp(16)) {
                        currentX = 0;
                        break;
                    } else {
                        isSlide = true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isSlide == true) {
                        int x=currentX;
                        currentX = (int) event.getX();
                        view.setTranslationX(currentX);
//                        scrollBy(x-currentX,0);
//                        scrollTo(-currentX,0);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (currentX <= SCREEN_WIDTH *0.6) {
                        handler.sendEmptyMessage(SLIPLEFT);
                    } else {
                        handler.sendEmptyMessage(SLIPRIGHT);
                    }
            }
            invalidate();
            return true;
        }
    }

    /* 界面移动 */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SLIPLEFT:
                    currentX -= dp(36);
                    if (currentX <= 0) {
                        currentX = 0;
                        view.setTranslationX(0);
                        invalidate();
                        isSlide = false;
                        return false;
                    } else {
                        view.setTranslationX(currentX);
                        invalidate();
                        handler.sendEmptyMessageDelayed(SLIPLEFT, 10);
                        return false;
                    }
                case SLIPRIGHT:
                    currentX += dp(36);
                    if (currentX >=SCREEN_WIDTH) {
                        view.setTranslationX(SCREEN_WIDTH);
                        invalidate();
                        activity.finish();
                    } else {
                        view.setTranslationX(currentX);
                        invalidate();
                        handler.sendEmptyMessageDelayed(SLIPRIGHT, 10);
                        return false;
                    }
            }
            invalidate();
            return false;
        }
    });

    /* 替换原根布局 */
    public void replace() {
        viewGroup.removeView(view);
        this.addView(view);
        viewGroup.addView(this);
    }

    /* 进行阴影绘制,onDraw（）方法在ViewGroup中不一定会执行 */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Shader shader = new LinearGradient(currentX - dp(12), 0, currentX, 0,
                new int[]{Color.parseColor("#00666666"),
                        Color.parseColor("#22666666"),
                        Color.parseColor("#50666666"),
                        Color.parseColor("#80666666")},
                null, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        RectF rectF = new RectF(currentX - dp(10), 0, currentX, SCREEN_HEIGHT);
        canvas.drawRect(rectF, paint);
    }

    private float dp(float px){
        float scale = getContext().getResources().getDisplayMetrics().density;
        return px*scale+0.5f;
    }
}


