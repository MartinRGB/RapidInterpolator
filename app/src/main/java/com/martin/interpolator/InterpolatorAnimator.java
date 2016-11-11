package com.martin.interpolator;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by MartinRGB on 2016/11/10.
 */

public class InterpolatorAnimator {


    private  ObjectAnimator manimator;

    public InterpolatorAnimator(View view, String string, float fromValue, float toValue, Interpolator interpolator, long duration,long delayTime) {

        manimator = ObjectAnimator.ofFloat(view, string, fromValue, toValue);
        manimator.setInterpolator(interpolator);
        manimator.setDuration(duration);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                manimator.start();

            }
        },delayTime);
    };

}

