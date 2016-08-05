package com.sfexpress.gradletest;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    public static final String FEATURE_ENCRYPTION_ENABLE = "feature.encryption.enabled";
    public static final String PROXY_SERVER = "proxy.server.address";
    public static final String DEFAULT_PROXY_SERVER_ADDRESS = "10.0.23.32:10010";
    public static final String KEY_VERSION_NAME = "version.name";
    private static final String DEFAULT_VERSION_NAME = "TEST V1.0";
    public static final String DEFAULT_FEATURE_TOGGLE_VALUE = "false";

    private static PropertyUtil instance;
    private Properties properties;

    private PropertyUtil(Context context) {
        init(context);
    }

    public static PropertyUtil propertyUtil(Context context) {
        if (instance == null) {
            instance = new PropertyUtil(context.getApplicationContext());
        }
        return instance;
    }

    public String getProxyServerAddress() {
        return properties.getProperty(PROXY_SERVER, DEFAULT_PROXY_SERVER_ADDRESS);
    }

    private boolean getBoolean(String featureName, String defaultValue) {
        String enabled = properties.getProperty(featureName, defaultValue);
        return Boolean.valueOf(enabled);
    }

    private void init(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.app_config);

        properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersionName() {
        return properties.getProperty(KEY_VERSION_NAME, DEFAULT_VERSION_NAME);
    }

    public boolean isFeatureEnabled(String featureName) {
        return getBoolean(featureName, DEFAULT_FEATURE_TOGGLE_VALUE);
    }
}
