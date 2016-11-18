# RapidInterpolator
A Java library that adjust interpolator dynamicly(Inspired by Facebook Rebound)

As a UI Prototype Designer & Engineer.The most painful part is compiling.After I finished the code work.I had to wait for about 10 seconds. So I write this for adjust animation's Parameter efficiently.

## Example:
![Show](https://github.com/MartinRGB/RapidInterpolator/blob/master/example.gif?raw=true)

## Import:
1.Add these into your project.
```
InterpolatorAnimator.java
InterpolatorConfig.java
InterpolatorConfigRegistry.java
InterpolatorConfigurationView.java 
```
2.Import them in your MainActivity
```
import com.martin.interpolator.InterpolatorAnimator;
import com.martin.interpolator.InterpolatorConfig;
import com.martin.interpolator.InterpolatorConfigRegistry;
import com.martin.interpolator.InterpolatorConfigurationView;
```

## Usage:
1.In you layout xml,add this:
```
<com.martin.interpolator.InterpolatorConfigurationView
    android:id="@+id/interpolator_configurator"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom"
    android:layout_alignParentBottom="true"
    android:layout_alignParentStart="true">
</com.martin.interpolator.InterpolatorConfigurationView>
```
2.Create a function to set Interpolator Config & add ConfiguratorView

```
private void interpolatorConfig(){

    //Find InterpolatorConfigurationView in XML
    mInterpolatorConfiguratorView = (InterpolatorConfigurationView) findViewById(R.id.interpolator_configurator);

    //Setting Interpolator Config
    config0 = InterpolatorConfig.fromInterpolatorDurationDelayTime("ElasticEaseInOut",200,100);
    config1 = InterpolatorConfig.fromInterpolatorDurationDelayTime("CubicEaseInOut",300,150);
    config2 = InterpolatorConfig.fromInterpolatorDurationDelayTime("BounceEaseInOut",420,20);
    config3 = InterpolatorConfig.fromInterpolatorDurationDelayTime("DecelerateCubic",230,120);
    config4 = InterpolatorConfig.fromInterpolatorDurationDelayTime("FastOutLinearIn",320,40);
    config5 = InterpolatorConfig.fromInterpolatorDurationDelayTime("CircEaseOut",280,80);
    config6 = InterpolatorConfig.fromInterpolatorDurationDelayTime("AnticipateOvershoot",250,50);
    config7 = InterpolatorConfig.fromInterpolatorDurationDelayTime("FastOutSlowIn",375,200);
    config8 = InterpolatorConfig.fromInterpolatorDurationDelayTime("BackEaseIn",275,150);

    //Add Interpolator Config into ConfigurationView
    InterpolatorConfigRegistry.getInstance().removeAllInterpolatorConfig();
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config0, "Scale_Anim_1");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config1, "Scale_Anim_2");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config2, "Scale_Anim_3");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config3, "Scale_Anim_4");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config4, "Scale_Anim_5");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config5, "Scale_Anim_6");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config6, "Scale_Anim_7");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config7, "Scale_Anim_8");
    InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config8, "Scale_Anim_9");

    mInterpolatorConfiguratorView.refreshInterpolatorConfigurations();
    mInterpolatorConfiguratorView.bringToFront();
}
```

3.Use it directly!
```
findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        mIsOut = !mIsOut;
        float startValue = mIsOut?1f:0f;
        float endValue = mIsOut?0f:1f;

        InterpolatorAnimator animator = new InterpolatorAnimator(view, "translationY", startValue, endValue,config0.interpolator,config0.duration,config0.delayTime);


    }
});
```

## Types of Interpolator
47 different easing interpolators(Which contains native interpolators & interpolators from [Easing.net](http://easings.net/zh-cn))
```
"Linear",
"LinearOutSlowIn",
"FastOutLinearIn",
"FastOutSlowIn",
"AccelerateQuad",
"AccelerateCubic",
"AccelerateQuint",
"AccelerateDecelerate",
"DecelerateQuad",
"DecelerateCubic",
"DecelerateQuint",
"Anticipate",
"AnticipateOvershoot",
"Overshoot",
"Bounce",
"Cycle",
"LinearEaseNone",
"SineEaseIn",
"SineEaseOut",
"SineEaseInOut",
"QuadEaseIn",
"QuadEaseOut",
"QuadEaseInOut",
"CubicEaseIn",
"CubicEaseOut",
"CubicEaseInOut",
"QuartEaseIn",
"QuartEaseOut",
"QuartEaseInOut",
"QuintEaseIn",
"QuintEaseOut",
"QuintEaseInOut",
"ExpoEaseIn",
"ExpoEaseOut",
"ExpoEaseInOut",
"CircEaseIn",
"CircEaseOut",
"CircEaseInOut",
"BackEaseIn",
"BackEaseOut",
"BackEaseInOut",
"ElasticEaseIn",
"ElasticEaseOut",
"ElasticEaseInOut",
"BounceEaseIn",
"BounceEaseOut",
"BounceEaseInOut"
```

## Notice
In the latest update,I added a typeface into the project,don't forget to grab it into your project,or it will be crushed...

## Thanks
Special thanks to [CymChad](https://github.com/CymChad/BaseRecyclerViewAdapterHelper),he gave me lots of help in java coding.




