package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.ilungj.android.shoppingtraffic.R;

/**
 * Created by Il Ung on 1/26/2017.
 */

public class BlankFragment extends CustomFragment {

    private float mx, my;

    private ScrollView vScroll;
    private HorizontalScrollView hScroll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
//        vScroll = (ScrollView) view.findViewById(R.id.vScroll);
//        hScroll = (HorizontalScrollView) view.findViewById(R.id.hScroll);
//
//        view.setOnTouchListener(new CustomTouchListener());
        return view;
    }

    private class CustomTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d("CTL", "CALLED");
            float curX, curY;

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mx = event.getX();
                    my = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    curX = event.getX();
                    curY = event.getY();
                    vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    mx = curX;
                    my = curY;
                    break;
                case MotionEvent.ACTION_UP:
                    curX = event.getX();
                    curY = event.getY();
                    vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                    break;
            }

            return true;
        }
    }

}
