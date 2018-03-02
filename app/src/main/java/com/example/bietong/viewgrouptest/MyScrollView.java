package com.example.bietong.viewgrouptest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by BieTong on 2018/1/24.
 * 仿ScrollView
 * 通过重设子View的top与bottom，设置每个子View高度为屏幕高度
 * 依次排列
 */

public class MyScrollView extends ViewGroup {
    private int SCREEN_HEIGHT;
    private Context context;
    private int oldY;
    private int currentY = 0;
    private Scroller scroller;//滑动帮助类

    public MyScrollView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        SCREEN_HEIGHT = getContext().getResources().getDisplayMetrics().heightPixels;
        scroller = new Scroller(getContext());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!scroller.isFinished()) {
                            scroller.abortAnimation();//终止动画
                        }
                        int dy=oldY-(int)event.getY();
                        if(getScrollY()<0){
                            scrollTo(0,0);
                            dy=0;
                        }
                        /*if(getScrollY()>getHeight()-SCREEN_HEIGHT){
                            dy=0;
                        }*/
                        scrollBy(0,dy);
                        oldY=(int) event.getY();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        {//设置ViewGroup高度
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
            marginLayoutParams.height = SCREEN_HEIGHT * childCount;
            this.setLayoutParams(marginLayoutParams);
        }
        {//遍历以设置子View放置位置
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    child.layout(0, i * SCREEN_HEIGHT, r, (i + 1) * SCREEN_HEIGHT);
                    //设置View位置，坐标相对父容器
                }
            }
        }

    }

    /* 遍历测量子View */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                intercepted=true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return intercepted;
    }
}
