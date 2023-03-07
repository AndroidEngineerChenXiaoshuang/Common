package com.jixingmao.common.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jixingmao.common.utils.ContextUtils;
import com.jixingmao.common.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeaderInterceptor implements Interceptor {


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String accessToken = SharedPreferencesUtils.getString(ContextUtils.getContext()
                , SharedPreferencesUtils.key_access_token, null);
        if (!TextUtils.isEmpty(accessToken)) {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            return chain.proceed(request);
        }
        return chain.proceed(chain.request());
    }
}
