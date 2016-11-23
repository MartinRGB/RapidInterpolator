package com.martin.interpolator;

/**
 * Created by MartinRGB on 2016/11/11.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The SpringConfiguratorView provides a reusable view for live-editing all registered springs
 * within an Application. Each registered Spring can be accessed by its id and its tension and
 * friction properties can be edited while the user tests the effected UI live.
 */
public class InterpolatorConfigurationView extends FrameLayout {

    private static final int MAX_SEEKBAR_VAL = 100000;
    private static final float MIN_DURATION = 0;
    private static final float MAX_DURATION = 1000;
    private static final float MIN_DELAYTIME = 0;
    private static final float MAX_DELAYTIME = 2000;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    private final SpinnerAdapter spinnerAdapter;
    private final List<InterpolatorConfig> mInterpolatorConfigs = new ArrayList<InterpolatorConfig>();
    //private final Spring mRevealerSpring;
    private final float mStashPx;
    private final float mRevealPx;
    private boolean mIsOut = false;
    private final InterpolatorConfigRegistry interpolatorConfigRegistry;
    private final int mTextColor = Color.argb(255, 255, 255, 255);
    private final int mTextSize = 16;
    private final Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/DIN-Bold.otf");
    private SeekBar mDurationSeekBar;
    private SeekBar mDelayTimeSeekBar;
    private Spinner mInterpolatorSelectorSpinner;
    private TextView mDurationLabel;
    private TextView mDelayTimenLabel;
    private InterpolatorConfig mSelectedInterpolatorConfig;

    private Spinner mCurveSelectorSpinner;
    public Interpolator mInterpolators[];
    private Interpolator newInterpolator;
    public static Map<String, Interpolator> interpolatorMap = new HashMap<>();
    public static String[] interpolatorNames = {
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
            "BounceEaseInOut"};

    public InterpolatorConfigurationView(Context context) {
        this(context, null);
    }

    public InterpolatorConfigurationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public InterpolatorConfigurationView(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        interpolatorConfigRegistry = InterpolatorConfigRegistry.getInstance();
        spinnerAdapter = new SpinnerAdapter(context);

        Resources resources = getResources();
        mRevealPx = dpToPx(40, resources);
        mStashPx = dpToPx(280, resources);


        addView(generateHierarchy(context));

        SeekbarListener seekbarListener = new SeekbarListener();
        mDurationSeekBar.setMax(MAX_SEEKBAR_VAL);
        mDurationSeekBar.setOnSeekBarChangeListener(seekbarListener);

        mDelayTimeSeekBar.setMax(MAX_SEEKBAR_VAL);
        mDelayTimeSeekBar.setOnSeekBarChangeListener(seekbarListener);

        mInterpolatorSelectorSpinner.setAdapter(spinnerAdapter);
        mInterpolatorSelectorSpinner.setOnItemSelectedListener(new InterpolatorSelectedListener());

        mInterpolators = new Interpolator[]{
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.linear),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.linear_out_slow_in),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.fast_out_linear_in),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.fast_out_slow_in),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.accelerate_quad),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.accelerate_cubic),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.accelerate_quint),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.accelerate_decelerate),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.decelerate_quad),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.decelerate_cubic),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.decelerate_quint),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.anticipate),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.anticipate_overshoot),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.overshoot),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.bounce),
                new AnimationUtils().loadInterpolator(context,
                        android.R.interpolator.cycle),
                new LinearEaseNoneInterpolater(),
                new SineEaseInInterpolater(),
                new SineEaseOutInterpolater(),
                new SineEaseInOutInterpolater(),
                new QuadEaseInInterpolater(),
                new QuadEaseOutInterpolater(),
                new QuadEaseInOutInterpolater(),
                new CubicEaseInInterpolater(),
                new CubicEaseOutInterpolater(),
                new CubicEaseInOutInterpolater(),
                new QuartEaseInInterpolater(),
                new QuartEaseOutInterpolater(),
                new QuartEaseInOutInterpolater(),
                new QuintEaseInInterpolater(),
                new QuintEaseOutInterpolater(),
                new QuintEaseInOutInterpolater(),
                new ExpoEaseInInterpolater(),
                new ExpoEaseInOutInterpolater(),
                new ExpoEaseOutInterpolater(),
                new CircEaseInInterpolater(),
                new CircEaseInOutInterpolater(),
                new CircEaseOutInterpolater(),
                new BackEaseInInterpolater(),
                new BackEaseInOutInterpolater(),
                new BackEaseOutInterpolater(),
                new ElasticEaseInInterpolater(),
                new ElasticEaseInOutInterpolater(),
                new ElasticEaseOutInterpolater(),
                new BounceEaseInInterpolater(),
                new BounceEaseInOutInterpolater(),
                new BounceEaseOutInterpolater()

        };


        //制作一个String(Names)与Interpolator对应的映射，让我们最能获取到 MainActivity 里面设置的插值器名称。
        for (int i = 0; i < interpolatorNames.length; i++) {
            interpolatorMap.put(interpolatorNames[i], mInterpolators[i]);
        }

        //2nd Spinner Setting
        ArrayAdapter<String> curvespinnerAdapter =
                new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, interpolatorNames){


                        public View getView(int position, View convertView, ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);
                            TextView tv = ((TextView) v);

                            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            tv.setLayoutParams(params);
                            tv.setSingleLine();

                            int twelvePx = dpToPx(12, getResources());
                            tv.setPadding(twelvePx, twelvePx, twelvePx, twelvePx);
                            //tv.setTextColor(mTextColor);
                            //tv.setTypeface(myTypeface);
                            tv.setTextSize(mTextSize);

                            return tv;
                        }

                };
        mCurveSelectorSpinner.setAdapter(curvespinnerAdapter);
        mCurveSelectorSpinner.setOnItemSelectedListener(new CurveSelectedListener());




        refreshInterpolatorConfigurations();


        this.setTranslationY(mStashPx);
    }

    /**
     * Programmatically build up the view hierarchy to avoid the need for resources.
     *
     * @return View hierarchy
     */
    private View generateHierarchy(Context context) {
        Resources resources = getResources();


        FrameLayout.LayoutParams params;
        int fivePx = dpToPx(5, resources);
        int tenPx = dpToPx(10, resources);
        int twentyPx = dpToPx(20, resources);
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f);
        tableLayoutParams.setMargins(0, 0, fivePx, 0);
        LinearLayout seekWrapper;

        FrameLayout root = new FrameLayout(context);
        params = createLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(300, resources));
        root.setLayoutParams(params);

        FrameLayout container = new FrameLayout(context);
        params = createMatchParams();
        params.setMargins(0, twentyPx, 0, 0);
        container.setLayoutParams(params);
        container.setBackgroundColor(Color.argb(100, 0, 0, 0));
        root.addView(container);

        mInterpolatorSelectorSpinner = new Spinner(context, Spinner.MODE_DIALOG);
        params = createMatchWrapParams();
        params.gravity = Gravity.TOP;
        params.setMargins(tenPx, tenPx, tenPx, 0);
        mInterpolatorSelectorSpinner.setLayoutParams(params);
        container.addView(mInterpolatorSelectorSpinner);


        mCurveSelectorSpinner = new Spinner(context, Spinner.MODE_DIALOG);
        params = createMatchWrapParams();
        params.gravity = Gravity.TOP;
        params.setMargins(tenPx, twentyPx * 7/2, tenPx, 0);
        mCurveSelectorSpinner.setLayoutParams(params);
        container.addView(mCurveSelectorSpinner);

        LinearLayout linearLayout = new LinearLayout(context);
        params = createMatchWrapParams();
        params.setMargins(0, 0, 0, dpToPx(40, resources));
        params.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        container.addView(linearLayout);

        seekWrapper = new LinearLayout(context);
        params = createMatchWrapParams();
        params.setMargins(tenPx, twentyPx, tenPx, twentyPx);
        seekWrapper.setPadding(tenPx, tenPx, tenPx, tenPx);
        seekWrapper.setLayoutParams(params);
        seekWrapper.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(seekWrapper);

        mDurationSeekBar = new SeekBar(context);
        mDurationSeekBar.setLayoutParams(tableLayoutParams);
        seekWrapper.addView(mDurationSeekBar);

        mDurationLabel = new TextView(getContext());
        //mDurationLabel.setTextColor(mTextColor);
        params = createLayoutParams(
                dpToPx(80, resources),
                ViewGroup.LayoutParams.MATCH_PARENT);
        mDurationLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        mDurationLabel.setLayoutParams(params);
        mDurationLabel.setMaxLines(1);
        mDurationLabel.setTextSize(14);
        //mDurationLabel.setTypeface(myTypeface);
        seekWrapper.addView(mDurationLabel);

        seekWrapper = new LinearLayout(context);
        params = createMatchWrapParams();
        params.setMargins(tenPx, tenPx, tenPx, twentyPx);
        seekWrapper.setPadding(tenPx, tenPx, tenPx, tenPx);
        seekWrapper.setLayoutParams(params);
        seekWrapper.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(seekWrapper);

        mDelayTimeSeekBar = new SeekBar(context);
        mDelayTimeSeekBar.setLayoutParams(tableLayoutParams);
        seekWrapper.addView(mDelayTimeSeekBar);

        mDelayTimenLabel = new TextView(getContext());
        //mDelayTimenLabel.setTextColor(mTextColor);
        params = createLayoutParams(dpToPx(80, resources), ViewGroup.LayoutParams.MATCH_PARENT);
        mDelayTimenLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        mDelayTimenLabel.setLayoutParams(params);
        mDelayTimenLabel.setMaxLines(1);
        mDelayTimenLabel.setTextSize(14);
        //mDelayTimenLabel.setTypeface(myTypeface);
        seekWrapper.addView(mDelayTimenLabel);

        View nub = new View(context);
        params = createLayoutParams(dpToPx(60, resources), dpToPx(40, resources));
        params.gravity = Gravity.TOP | Gravity.CENTER;
        nub.setLayoutParams(params);
        nub.setOnTouchListener(new OnNubTouchListener());
        nub.setBackgroundColor(Color.argb(255, 0, 164, 209));
        root.addView(nub);


        return root;
    }

    /**
     * remove the configurator from its parent and clean up springs and listeners
     */
    public void destroy() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        //mRevealerSpring.destroy();
    }

    /**
     * reload the springs from the registry and update the UI
     */
    public void refreshInterpolatorConfigurations() {
        Map<InterpolatorConfig, String> interpolatorConfigMap = interpolatorConfigRegistry.getAllInterpolatorConfig();

        spinnerAdapter.clear();
        mInterpolatorConfigs.clear();

        for (Map.Entry<InterpolatorConfig, String> entry : interpolatorConfigMap.entrySet()) {
            if (entry.getKey() == InterpolatorConfig.defaultConfig) {
                continue;
            }
            mInterpolatorConfigs.add(entry.getKey());
            spinnerAdapter.add(entry.getValue());

        }
        spinnerAdapter.notifyDataSetChanged();
        if (mInterpolatorConfigs.size() > 0) {
            mInterpolatorSelectorSpinner.setSelection(0);
        }
    }


    private class InterpolatorSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mSelectedInterpolatorConfig = mInterpolatorConfigs.get(i);
            updateSeekBarsForInterpolatorConfig(mSelectedInterpolatorConfig);

            //让Spinner选择对应Layer对应Config的位置号
            mCurveSelectorSpinner.setSelection(mSelectedInterpolatorConfig.interpolatorPosition);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }


    private class CurveSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            newInterpolator = mInterpolators[mCurveSelectorSpinner.getSelectedItemPosition()];
            updateInterpolatorConfigForSelect(mSelectedInterpolatorConfig);

            //根据Spinner控件的选取，拿到interpolator位置号
            mSelectedInterpolatorConfig.interpolatorPosition = mCurveSelectorSpinner.getSelectedItemPosition();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }


    /**
     * listen to events on seekbars and update registered springs accordingly
     */
    private class SeekbarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int val, boolean b) {
            float durationRange = MAX_DURATION - MIN_DURATION;
            float delayTimeRange = MAX_DELAYTIME - MIN_DELAYTIME;

            if (seekBar == mDurationSeekBar) {
                float scaledDuration = (int)((val) * durationRange) / MAX_SEEKBAR_VAL + MIN_DURATION;
                mSelectedInterpolatorConfig.duration = (long) scaledDuration;
                String roundedDurationLabel = DECIMAL_FORMAT.format(scaledDuration);
                mDurationLabel.setText("T: "+ roundedDurationLabel + "ms");
            }

            if (seekBar == mDelayTimeSeekBar) {
                float scaledDelayTime = (int)((val) * delayTimeRange) / MAX_SEEKBAR_VAL + MIN_DELAYTIME;
                mSelectedInterpolatorConfig.delayTime = (long) scaledDelayTime;
                String roundedDelayTimeLabel = DECIMAL_FORMAT.format(scaledDelayTime);
                mDelayTimenLabel.setText("D: "+ roundedDelayTimeLabel + "ms");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private void updateInterpolatorConfigForSelect(InterpolatorConfig interpolatorConfig) {
        mSelectedInterpolatorConfig.interpolator = newInterpolator;

    }


    private void updateSeekBarsForInterpolatorConfig(InterpolatorConfig interpolatorConfig) {
        float duration = interpolatorConfig.duration;
        float durationRange = MAX_DURATION - MIN_DURATION;
        int scaledDuration = Math.round(((duration - MIN_DURATION) * MAX_SEEKBAR_VAL) / durationRange);

        float delayTime = interpolatorConfig.delayTime;
        float delayTimeRange = MAX_DELAYTIME - MIN_DELAYTIME;
        int scaledDelayTime = Math.round(((delayTime - MIN_DELAYTIME) * MAX_SEEKBAR_VAL) / delayTimeRange);

        mDurationSeekBar.setProgress(scaledDuration);
        mDelayTimeSeekBar.setProgress(scaledDelayTime);
    }

    /**
     * toggle visibility when the nub is tapped.
     */
    private class OnNubTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                mIsOut = !mIsOut;
                float startY = mIsOut ? mStashPx : mRevealPx;
                float endY = mIsOut ? mRevealPx : mStashPx;
                InterpolatorAnimator thisInterpolator = new InterpolatorAnimator(InterpolatorConfigurationView.this, "translationY", startY, endY, new DecelerateInterpolator(), 300, 0);
            }
            return true;
        }
    }


    //1st Spinner Setting
    private class SpinnerAdapter extends BaseAdapter {

        private final Context mContext;
        private final List<String> mStrings;

        public SpinnerAdapter(Context context) {
            mContext = context;
            mStrings = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return mStrings.size();
        }

        @Override
        public Object getItem(int position) {
            return mStrings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(String string) {
            mStrings.add(string);
            notifyDataSetChanged();
        }

        /**
         * Remove all elements from the list.
         */
        public void clear() {
            mStrings.clear();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {

                textView = new TextView(mContext);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                int twelvePx = dpToPx(12, getResources());
                textView.setPadding(twelvePx, twelvePx, twelvePx, twelvePx);

                //textView.setTypeface(myTypeface);
                //textView.setTextColor(mTextColor);
                textView.setTextSize(mTextSize);
            } else {
                //parent.setBackgroundColor(Color.argb(100, 0, 0, 0));
                textView = (TextView) convertView;
            }
            textView.setText(mStrings.get(position));


            return textView;
        }
    }

    //Utils

    public static int dpToPx(float dp, Resources res) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                res.getDisplayMetrics());
    }

    public static FrameLayout.LayoutParams createLayoutParams(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }

    public static FrameLayout.LayoutParams createMatchParams() {
        return createLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams createWrapParams() {
        return createLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams createWrapMatchParams() {
        return createLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams createMatchWrapParams() {
        return createLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public Interpolator[] getInterpolators() {
        return mInterpolators;
    }


    public class BackEaseInInterpolater implements Interpolator {
        private final float mOvershot;

        public BackEaseInInterpolater() {
            mOvershot = 0;
        }

        /**
         * @param overshot
         */
        public BackEaseInInterpolater(float overshot) {
            mOvershot = overshot;
        }

        public float getInterpolation(float t) {
            float s = mOvershot == 0 ? 1.70158f : mOvershot;
            return t * t * ((s + 1) * t - s);
        }
    }

    public class BackEaseInOutInterpolater implements Interpolator {
        private final float mOvershot;

        public BackEaseInOutInterpolater() {
            mOvershot = 0;
        }

        /**
         * @param overshot
         */
        public BackEaseInOutInterpolater(float overshot) {
            mOvershot = overshot;
        }

        public float getInterpolation(float t) {
            float s = mOvershot == 0 ? 1.70158f : mOvershot;

            t *= 2;
            if (t < 1) {
                s *= (1.525);
                return 0.5f * (t * t * ((s + 1) * t - s));
            }

            t -= 2;
            s *= (1.525);
            return 0.5f * (t * t * ((s + 1) * t + s) + 2);
        }
    }

    public class BackEaseOutInterpolater implements Interpolator {
        private final float mOvershot;

        public BackEaseOutInterpolater() {
            mOvershot = 0;
        }

        /**
         * @param overshot
         */
        public BackEaseOutInterpolater(float overshot) {
            mOvershot = overshot;
        }

        public float getInterpolation(float t) {
            float s = mOvershot == 0 ? 1.70158f : mOvershot;
            t -= 1;
            return (t * t * ((s + 1) * t + s) + 1);
        }
    }

    public class BounceEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return 1 - new BounceEaseOutInterpolater().getInterpolation(1 - t);
        }
    }

    public class BounceEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            if (t < 0.5f) {
                return new BounceEaseInInterpolater().getInterpolation(t * 2) * 0.5f;
            } else {
                return new BounceEaseOutInterpolater().getInterpolation(t * 2 - 1) * 0.5f + 0.5f;
            }
        }
    }

    public class BounceEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            if (t < (1 / 2.75)) {
                return 7.5625f * t * t;
            } else if (t < (2 / 2.75)) {
                t -= (1.5 / 2.75);
                return 7.5625f * t * t + 0.75f;
            } else if (t < (2.5 / 2.75)) {
                t -= (2.25 / 2.75);
                return 7.5625f * t * t + 0.9375f;
            } else {
                t -= (2.625 / 2.75);
                return 7.5625f * t * t + 0.984375f;
            }
        }
    }

    public class CircEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return -(float) (Math.sqrt(1 - t * t) - 1);
        }
    }

    public class CircEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return (float) Math.sqrt(1 - (t -= 1) * t);
        }
    }

    public class CircEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t *= 2;
            if (t < 1) {
                return -0.5f * (float) (Math.sqrt(1 - t * t) - 1);
            }

            t -= 2;
            return 0.5f * (float) (Math.sqrt(1 - t * t) + 1);
        }
    }

    public class CubicEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return t * t * t;
        }
    }

    public class CubicEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t * t;
            }

            t -= 2;
            return 0.5f * (t * t * t + 2);
        }
    }

    public class CubicEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t -= 1;
            return t * t * t + 1;
        }
    }

    public class ElasticEaseInInterpolater implements Interpolator {
        private final float mAmplitude;
        private final float mPeriod;

        public ElasticEaseInInterpolater() {
            mAmplitude = 0;
            mPeriod = 0;
        }

        /**
         * @param amplitude
         * @param period
         */
        public ElasticEaseInInterpolater(float amplitude, float period) {
            mAmplitude = amplitude;
            mPeriod = period;
        }

        public float getInterpolation(float t) {
            float p = mPeriod;
            float a = mAmplitude;

            float s;
            if (t == 0) {
                return 0;
            }
            if (t == 1) {
                return 1;
            }
            if (p == 0) {
                p = 0.3f;
            }
            if (a == 0 || a < 1) {
                a = 1;
                s = p / 4;
            } else {
                s = (float) (p / (Math.PI * 2) * Math.asin(1 / a));
            }
            t -= 1;
            return -(float) (a * Math.pow(2, 10 * t) * Math.sin((t - s) * (Math.PI * 2) / p));
        }
    }

    public class ElasticEaseInOutInterpolater implements Interpolator {
        private final float mAmplitude;
        private final float mPeriod;

        public ElasticEaseInOutInterpolater() {
            mAmplitude = 0;
            mPeriod = 0;
        }

        /**
         * @param amplitude
         * @param period
         */
        public ElasticEaseInOutInterpolater(float amplitude, float period) {
            mAmplitude = amplitude;
            mPeriod = period;
        }

        public float getInterpolation(float t) {
            float p = mPeriod;
            float a = mAmplitude;

            float s;
            if (t == 0) {
                return 0;
            }

            t /= 0.5f;
            if (t == 2) {
                return 1;
            }
            if (p == 0) {
                p = 0.3f * 1.5f;
            }
            if (a == 0 || a < 1) {
                a = 1;
                s = p / 4;
            } else {
                s = (float) (p / (Math.PI * 2) * Math.asin(1 / a));
            }
            if (t < 1) {
                t -= 1;
                return -0.5f * (float) (a * Math.pow(2, 10 * t) * Math.sin((t - s) * (Math.PI *
                        2) / p));
            }

            t -= 1;
            return (float) (a * Math.pow(2, -10 * t) * Math.sin((t - s) * (Math.PI * 2) / p) *
                    0.5f + 1);
        }
    }

    public class ElasticEaseOutInterpolater implements Interpolator {
        private final float mAmplitude;
        private final float mPeriod;

        public ElasticEaseOutInterpolater() {
            mAmplitude = 0;
            mPeriod = 0;
        }

        /**
         * @param amplitude
         * @param period
         */
        public ElasticEaseOutInterpolater(float amplitude, float period) {
            mAmplitude = amplitude;
            mPeriod = period;
        }

        public float getInterpolation(float t) {
            float p = mPeriod;
            float a = mAmplitude;

            float s;
            if (t == 0) {
                return 0;
            }
            if (t == 1) {
                return 1;
            }
            if (p == 0) {
                p = 0.3f;
            }
            if (a == 0 || a < 1) {
                a = 1;
                s = p / 4;
            } else {
                s = (float) (p / (Math.PI * 2) * Math.asin(1 / a));
            }
            return (float) (a * Math.pow(2, -10 * t) * Math.sin((t - s) * (Math.PI * 2) / p) + 1);
        }
    }

    public class ExpoEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return (t == 0) ? 0 : (float) Math.pow(2, 10 * (t - 1));
        }
    }

    public class ExpoEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            if (t == 0) {
                return 0;
            }
            if (t == 1) {
                return 1;
            }

            t *= 2;
            if (t < 1) {
                return 0.5f * (float) Math.pow(2, 10 * (t - 1));
            }

            --t;
            return 0.5f * (float) (-Math.pow(2, -10 * t) + 2);
        }
    }

    public class ExpoEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return (t == 1) ? 1 : (float) (-Math.pow(2, -10 * t) + 1);
        }
    }

    public class LinearEaseNoneInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return t;
        }
    }

    public class QuadEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return t * t;
        }
    }

    public class QuadEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t;
            }
            --t;
            return -0.5f * (t * (t - 2) - 1);
        }
    }

    public class QuadEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return -t * (t - 2);
        }
    }

    public class QuartEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return t * t * t * t;
        }
    }

    public class QuartEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t * t * t;
            }
            t -= 2;
            return -0.5f * (t * t * t * t - 2);
        }
    }

    public class QuartEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t -= 1;
            return -(t * t * t * t - 1);
        }
    }

    public class QuintEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return t * t * t * t * t;
        }
    }

    public class QuintEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t * t * t * t;
            }
            t -= 2;
            return 0.5f * (t * t * t * t * t + 2);
        }
    }

    public class QuintEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            t -= 1;
            return (t * t * t * t * t + 1);
        }
    }

    public class SineEaseInInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return -(float) Math.cos(t * (Math.PI / 2)) + 1;
        }
    }

    public class SineEaseInOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return -0.5f * (float) (Math.cos(Math.PI * t) - 1);
        }
    }

    public class SineEaseOutInterpolater implements Interpolator {
        public float getInterpolation(float t) {
            return (float) Math.sin(t * (Math.PI / 2));
        }
    }

    public static int getPosition(String interpolatorName) {
        int pos = 0;
        for (int i = 0; i < interpolatorNames.length; i++) {
            if (interpolatorNames[i].equals(interpolatorName)) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}