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
    private InterpolatorConfig config1;

    private boolean backISOUT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteBars();
        setContentView(R.layout.activity_main);
        springConfig();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void deleteBars() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


    private void springConfig(){

        mInterpolatorConfiguratorView = (InterpolatorConfigurationView) findViewById(R.id.interpolator_configurator);

        //设置动画设置参数
        config1 = InterpolatorConfig.fromInterpolatorDurationDelayTime("ElasticEaseInOut",200,100);

        //将动画设置添加到配置视图中
        InterpolatorConfigRegistry.getInstance().removeAllInterpolatorConfig();
        InterpolatorConfigRegistry.getInstance().addInterpolatorConfig(config1, "数值1");

        mInterpolatorConfiguratorView.refreshInterpolatorConfigurations();
        mInterpolatorConfiguratorView.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {

        backISOUT = !backISOUT;

        float startValue = backISOUT?1f:0.5f;
        float endValue = backISOUT?0.5f:1f;


        //设置动画
        InterpolatorAnimator thisInterpolator = new InterpolatorAnimator(findViewById(R.id.button), "scaleX", startValue, endValue,config1.interpolator,config1.duration,config1.delayTime);


    }
}
