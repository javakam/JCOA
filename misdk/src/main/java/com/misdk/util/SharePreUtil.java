package com.misdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类<p>
 * 我们自己创建的   context.getSharedPreference(name, mode);
 * /data/data/YOUR_PACKAGE_NAME/shared_prefs/YOUR_PREFS_NAME.xml<p>
 * 默认   PreferenceManager.getDefaultSharedPreferences(context);
 * /data/data/YOUR_PACKAGE_NAME/shared_prefs/YOUR_PACKAGE_NAME_preferences.xml<p>
 * Created by javakam on 2016-07-08.
 */
public class SharePreUtil {
    private Context mContext;
    private SharedPreferences sp = null;
    private Editor edit = null;

    /**
     * Create DefaultSharedPreferences
     *
     * @param context
     */
    public SharePreUtil(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Create SharedPreferences by filename
     *
     * @param context
     * @param filename
     */
    public SharePreUtil(Context context, String filename) {
        this(context, context.getSharedPreferences(filename, Context.MODE_PRIVATE));
    }

    /**
     * Create SharedPreferences by SharedPreferences
     *
     * @param context
     * @param sp
     */
    public SharePreUtil(Context context, SharedPreferences sp) {
        this.mContext = context;
        this.sp = sp;
        edit = sp.edit();
    }

    // ==============================Set=============================//

    // Boolean
    public void setValue(String key, boolean value) {
        edit.putBoolean(key, value);
    }

    public void setValue(int resKey, boolean value) {
        setValue(this.mContext.getString(resKey), value);
    }

    // Float
    public void setValue(String key, float value) {
        edit.putFloat(key, value);
    }

    public void setValue(int resKey, float value) {
        setValue(this.mContext.getString(resKey), value);
    }

    // Integer
    public void setValue(String key, int value) {
        edit.putInt(key, value);
    }

    public void setValue(int resKey, int value) {
        setValue(this.mContext.getString(resKey), value);
    }

    // Long
    public void setValue(String key, long value) {
        edit.putLong(key, value);
    }

    public void setValue(int resKey, long value) {
        setValue(this.mContext.getString(resKey), value);
    }

    // String
    public void setValue(String key, String value) {
        edit.putString(key, value);
    }

    public void setValue(int resKey, String value) {
        setValue(this.mContext.getString(resKey), value);
    }

    // ==============================Get=============================//

    // Boolean
    public boolean getValue(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public boolean getValue(int resKey, boolean defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    // Float
    public float getValue(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public float getValue(int resKey, float defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    // Integer
    public int getValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public int getValue(int resKey, int defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    // Long
    public long getValue(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public long getValue(int resKey, long defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    // String
    public String getValue(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    // String  defaultValue  null
    public String getValue(String key) {
        return getValue(key, null);
    }

    public String getValue(int resKey, String defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    // ==============================Delete=============================//

    public void remove(String key) {
        edit.remove(key);
    }

    public void clear() {
        edit.clear();
    }

    // ==============================Commit=============================//

    /**
     * 提交
     */
    public void commit() {
        edit.commit();
    }

}
