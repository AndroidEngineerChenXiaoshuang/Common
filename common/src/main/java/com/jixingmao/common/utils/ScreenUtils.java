package com.jixingmao.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import me.jessyan.autosize.AutoSizeConfig;

public class ScreenUtils {

    public static float dpToPx(float dp) {
        DisplayMetrics displayMetrics = AutoSizeConfig.getInstance().getApplication()
                .getResources().getDisplayMetrics();
        LogUtils.i("ScreenUtils displayMetrics :" + displayMetrics.toString());
        return dp * displayMetrics.density;
    }

    public static float pxToDp(float px) {
        DisplayMetrics displayMetrics = AutoSizeConfig.getInstance().getApplication()
                .getResources().getDisplayMetrics();
        LogUtils.i("ScreenUtils displayMetrics :" + displayMetrics.toString());
        return px / displayMetrics.density;
    }
}
