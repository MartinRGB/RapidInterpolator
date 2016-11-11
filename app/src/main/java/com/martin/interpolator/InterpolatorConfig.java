package com.martin.interpolator;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by MartinRGB on 2016/11/11.
 */

public class InterpolatorConfig {

    public Interpolator interpolator;
    public long duration;
    public long delayTime;
    public int interpolatorPosition;

    public static InterpolatorConfig defaultConfig = new InterpolatorConfig(new LinearInterpolator(),300,0,0);

    public InterpolatorConfig(Interpolator interpolator, long duration,long delayTime,int interpolatorPosition) {
        this.interpolator = interpolator;
        this.duration = duration;
        this.delayTime = delayTime;
        this.interpolatorPosition = interpolatorPosition;
    }

    public static InterpolatorConfig fromInterpolatorDurationDelayTime(String string,long duration,long delayTime) {

        return new InterpolatorConfig(InterpolatorConfigurationView.interpolatorMap.get(string),duration,delayTime,InterpolatorConfigurationView.getPosition(string));

    }




}
