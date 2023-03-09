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
        Request.Builder builder = chain.request().newBuilder();

        if (!TextUtils.isEmpty(accessToken)) {
            builder.addHeader("Authorization", "Bearer " + accessToken);
        }

        return chain.proceed(builder.build());
    }
}
