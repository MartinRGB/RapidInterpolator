package com.example.martinrgb.rapidinterpolaotr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.martin.interpolator.InterpolatorAnimator;
import com.martin.interpolator.InterpolatorConfig;
import com.martin.interpolator.InterpolatorConfigRegistry;
import com.martin.interpolator.InterpolatorConfigurationView;


public class MainActivity extends Activity {

    //初始化配置视图和动画Config
    private InterpolatorConfigurationView mInterpolatorConfiguratorView;
    private InterpolatorConfig config0;
    private InterpolatorConfig config1;
    private InterpolatorConfig config2;
    private InterpolatorConfig config3;
    private InterpolatorConfig config4;
    private InterpolatorConfig config5;
    private InterpolatorConfig config6;
    private InterpolatorConfig config7;
    private InterpolatorConfig config8;

    private GridView mgridview;

    private boolean mIsOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteBars();
        setContentView(R.layout.activity_main);
        interpolatorConfig();

        mgridview = (GridView) findViewById(R.id.gridview);
        mgridview.setAdapter(new ImageAdapter(this));

        mgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });




        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIsOut = !mIsOut;
                float startValue = mIsOut?1f:0f;
                float endValue = mIsOut?0f:1f;

                final View[] itemArray = {mgridview.getChildAt(0),mgridview.getChildAt(1),mgridview.getChildAt(2),mgridview.getChildAt(3),mgridview.getChildAt(4),mgridview.getChildAt(5),mgridview.getChildAt(6),mgridview.getChildAt(7),mgridview.getChildAt(8)};
                final InterpolatorConfig[] configArray = {config0,config1,config2,config3,config4,config5,config6,config7,config8};

                InterpolatorAnimator animatorX0 = new InterpolatorAnimator(itemArray[0], "scaleX", startValue, endValue,configArray[0].interpolator,configArray[0].duration,configArray[0].delayTime);
                InterpolatorAnimator animatorY0 = new InterpolatorAnimator(itemArray[0], "scaleY", startValue, endValue,configArray[0].interpolator,configArray[0].duration,configArray[0].delayTime);
                InterpolatorAnimator animatorX1 = new InterpolatorAnimator(itemArray[1], "scaleX", startValue, endValue,configArray[1].interpolator,configArray[1].duration,configArray[1].delayTime);
                InterpolatorAnimator animatorY1 = new InterpolatorAnimator(itemArray[1], "scaleY", startValue, endValue,configArray[1].interpolator,configArray[1].duration,configArray[1].delayTime);
                InterpolatorAnimator animatorX2 = new InterpolatorAnimator(itemArray[2], "scaleX", startValue, endValue,configArray[2].interpolator,configArray[2].duration,configArray[2].delayTime);
                InterpolatorAnimator animatorY2 = new InterpolatorAnimator(itemArray[2], "scaleY", startValue, endValue,configArray[2].interpolator,configArray[2].duration,configArray[2].delayTime);
                InterpolatorAnimator animatorX3 = new InterpolatorAnimator(itemArray[3], "scaleX", startValue, endValue,configArray[3].interpolator,configArray[3].duration,configArray[3].delayTime);
                InterpolatorAnimator animatorY3 = new InterpolatorAnimator(itemArray[3], "scaleY", startValue, endValue,configArray[3].interpolator,configArray[3].duration,configArray[3].delayTime);
                InterpolatorAnimator animatorX4 = new InterpolatorAnimator(itemArray[4], "scaleX", startValue, endValue,configArray[4].interpolator,configArray[4].duration,configArray[4].delayTime);
                InterpolatorAnimator animatorY4 = new InterpolatorAnimator(itemArray[4], "scaleY", startValue, endValue,configArray[4].interpolator,configArray[4].duration,configArray[4].delayTime);
                InterpolatorAnimator animatorX5 = new InterpolatorAnimator(itemArray[5], "scaleX", startValue, endValue,configArray[5].interpolator,configArray[5].duration,configArray[5].delayTime);
                InterpolatorAnimator animatorY5 = new InterpolatorAnimator(itemArray[5], "scaleY", startValue, endValue,configArray[5].interpolator,configArray[5].duration,configArray[5].delayTime);
                InterpolatorAnimator animatorX6 = new InterpolatorAnimator(itemArray[6], "scaleX", startValue, endValue,configArray[6].interpolator,configArray[6].duration,configArray[6].delayTime);
                InterpolatorAnimator animatorY6 = new InterpolatorAnimator(itemArray[6], "scaleY", startValue, endValue,configArray[6].interpolator,configArray[6].duration,configArray[6].delayTime);
                InterpolatorAnimator animatorX7 = new InterpolatorAnimator(itemArray[7], "scaleX", startValue, endValue,configArray[7].interpolator,configArray[7].duration,configArray[7].delayTime);
                InterpolatorAnimator animatorY7 = new InterpolatorAnimator(itemArray[7], "scaleY", startValue, endValue,configArray[7].interpolator,configArray[7].duration,configArray[7].delayTime);
                InterpolatorAnimator animatorX8 = new InterpolatorAnimator(itemArray[8], "scaleX", startValue, endValue,configArray[8].interpolator,configArray[8].duration,configArray[8].delayTime);
                InterpolatorAnimator animatorY8 = new InterpolatorAnimator(itemArray[8], "scaleY", startValue, endValue,configArray[8].interpolator,configArray[8].duration,configArray[8].delayTime);


            }
        });



    }

    private void deleteBars() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


    private void interpolatorConfig(){

        mInterpolatorConfiguratorView = (InterpolatorConfigurationView) findViewById(R.id.interpolator_configurator);

        //设置动画设置参数
        config0 = InterpolatorConfig.fromInterpolatorDurationDelayTime("ElasticEaseInOut",200,100);
        config1 = InterpolatorConfig.fromInterpolatorDurationDelayTime("CubicEaseInOut",300,150);
        config2 = InterpolatorConfig.fromInterpolatorDurationDelayTime("BounceEaseInOut",420,20);
        config3 = InterpolatorConfig.fromInterpolatorDurationDelayTime("DecelerateCubic",230,120);
        config4 = InterpolatorConfig.fromInterpolatorDurationDelayTime("FastOutLinearIn",320,40);
        config5 = InterpolatorConfig.fromInterpolatorDurationDelayTime("CircEaseOut",280,80);
        config6 = InterpolatorConfig.fromInterpolatorDurationDelayTime("AnticipateOvershoot",250,50);
        config7 = InterpolatorConfig.fromInterpolatorDurationDelayTime("FastOutSlowIn",375,200);
        config8 = InterpolatorConfig.fromInterpolatorDurationDelayTime("BackEaseIn",275,150);

        //将动画设置添加到配置视图中
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



}
