package com.jixingmao.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.jixingmao.common.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void i(String text) {
        if (!TextUtils.isEmpty(text)) {
            Log.i(BuildConfig.LOG_TAG, text);
        }
    }

    public static void e(String text) {
        if (!TextUtils.isEmpty(text)) {
            Log.e(BuildConfig.LOG_TAG, text);
        }
    }

}
