package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.ilungj.android.shoppingtraffic.R;

/**
 * Created by Il Ung on 1/3/2017.
 */

public class MainFragment extends CustomFragment {

    Context context;

    AnimationSet[] animationSet;
    ImageView[] bubbles = new ImageView[7];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        context = this.getContext();

        animationSet = new AnimationSet[bubbles.length];
        for(int i = 0; i < bubbles.length; i++) {
            animationSet[i] = new AnimationSet(false);
            animationSet[i].setFillAfter(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_main, container, false);

        for(int i = 0; i < bubbles.length; i++) {
            int id = getResources().getIdentifier("bubble" + i, "id", this.context.getPackageName());
            bubbles[i] = (ImageView) newView.findViewById(id);
            bubbles[i].setImageResource(R.drawable.circle);
            bubbles[i].setAnimation(animationSet[i]);
            (new AnimationRepeater(animationSet[i], bubbles[i])).repeatAnimation();
        }

        return newView;
    }

}

