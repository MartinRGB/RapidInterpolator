package com.martin.interpolator;

/**
 * Created by MartinRGB on 2016/11/11.
 */


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * class for maintaining a registry of all interpolator configs
 */

public class InterpolatorConfigRegistry {

    private static final InterpolatorConfigRegistry INSTANCE = new InterpolatorConfigRegistry(true);

    public static InterpolatorConfigRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<InterpolatorConfig, String> mInterpolatorConfigMap;

    /**
     * constructor for the SpringConfigRegistry
     */
    InterpolatorConfigRegistry(boolean includeDefaultEntry) {
        mInterpolatorConfigMap = new HashMap<InterpolatorConfig, String>();
        if (includeDefaultEntry) {
            addInterpolatorConfig(InterpolatorConfig.defaultConfig, "default config");
        }
    }

    public boolean addInterpolatorConfig(InterpolatorConfig interpolatorConfig, String configName) {
        if (interpolatorConfig == null) {
            throw new IllegalArgumentException("interpolatorConfig is required");
        }
        if (configName == null) {
            throw new IllegalArgumentException("configName is required");
        }
        if (mInterpolatorConfigMap.containsKey(interpolatorConfig)) {
            return false;
        }
        mInterpolatorConfigMap.put(interpolatorConfig, configName);
        return true;
    }

    public boolean removeInterpolatorConfig(InterpolatorConfig interpolatorConfig) {
        if (interpolatorConfig == null) {
            throw new IllegalArgumentException("interpolatorConfig is required");
        }
        return mInterpolatorConfigMap.remove(interpolatorConfig) != null;
    }

    public Map<InterpolatorConfig, String> getAllInterpolatorConfig() {
        return Collections.unmodifiableMap(mInterpolatorConfigMap);
    }

    public void removeAllInterpolatorConfig() {
        mInterpolatorConfigMap.clear();
    }
}