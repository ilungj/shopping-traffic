package com.ilungj.android.shoppingtraffic;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Il Ung on 1/2/2017.
 */

public class CustomViewPager extends ViewPager {

    OnSwipeOutListener onSwipeOutListener;
    Fragment currentFragment;

    float startPos;
    float currentPos;
    boolean isSwipingUp;

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment f) {
        currentFragment = f;
    }

    void init() {
        setPageTransformer(true, new PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setTranslationX(getWidth() * -position);
                page.setTranslationY(getHeight() * position);
            }
        });
    }

    MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        onSwipeOutListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));

        swapXY(ev);
        //return super.onInterceptTouchEvent(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch(ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startPos = ev.getY();
                /*
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    startPos = ev.getY();
                } else {
                    startPos = ev.getX();
                }
                */

                Log.d("startX", startPos+"");
                return true;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                currentPos = ev.getY();
                /*
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    currentPos = ev.getY();
                } else {
                    Log.d("Configuration", "working");
                    currentPos = ev.getX();
                }
                */

                Log.d("currentX", currentPos+"");

                if(currentPos < startPos) {
                    isSwipingUp = false;
                    Log.d("Test", "I'm swiping down");
                }

                if(currentPos > startPos) {
                    isSwipingUp = true;
                    Log.d("Test", "I'm swiping up");
                }

                startPos = 0;
                break;
        }

        return super.onTouchEvent(swapXY(ev));
    }

    public interface OnSwipeOutListener {
        void onSwipeOutLeft();
        void onSwipeOutRight();
    }
}
