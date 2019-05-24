package com.example.tianqiyubao.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.litepal.LitePalApplication;


/**
 * Created by 史航
 * on 2017/12/20-14:13
 */

public class SharePreferenceUtil {

    public static void putString(String key, String value) {
        //if (!StringUtil.isEmpty(value))
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public static void putString(String key, String value, boolean isAllowNull) {
        if (isAllowNull) {
            getSharedPreferences().edit().putString(key, value).apply();
        } else {
            putString(key, value);
        }
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    private static SharedPreferences getSharedPreferences() {

        return LitePalApplication.getContext().getSharedPreferences("weather", Context.MODE_PRIVATE);
    }
}
