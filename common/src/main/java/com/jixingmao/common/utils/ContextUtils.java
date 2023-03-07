package com.jixingmao.common.utils;

import android.content.Context;


public class ContextUtils {
    private static Context mContext;

    public static void init(Context context) {
        ContextUtils.mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
