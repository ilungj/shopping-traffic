package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Il Ung on 12/31/2016.
 */

public class AnimationRepeater {

    AnimationSet animationSet;
    ImageView imageView;

    public AnimationRepeater(AnimationSet as, ImageView iv) {
        animationSet = as;
        imageView = iv;
    }

    public void repeatAnimation() {

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                animationSet.getAnimations().clear();

                long duration = 1000 * (new Random().nextInt(4) + 1);

                AlphaAnimation alpha = new AlphaAnimation(1.0f, 0);
                alpha.setInterpolator(new AccelerateInterpolator());
                alpha.setDuration(duration);

                TranslateAnimation translate = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, -0.1f * (new Random().nextInt(2) + 4));
                translate.setDuration(duration);

                animationSet.addAnimation(alpha);
                animationSet.addAnimation(translate);

                imageView.startAnimation(animationSet);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
