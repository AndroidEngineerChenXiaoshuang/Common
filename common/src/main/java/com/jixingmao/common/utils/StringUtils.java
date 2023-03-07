package com.jixingmao.common.utils;

import android.text.TextUtils;

public class StringUtils {
    public static String getLastSubString(int last, String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        if (value.length() <= last) {
            return value;
        }
        return value.substring(value.length() - last);
    }
}
