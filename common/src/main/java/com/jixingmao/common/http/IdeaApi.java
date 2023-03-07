package com.jixingmao.common.http;

import android.content.Context;

import retrofit2.Retrofit;

public class IdeaApi {
    public static <T> T getApiService(Class<T> cls, Context context, String baseUrl) {
        Retrofit retrofit = RetrofitUtils.getRetrofitBuilder(context, baseUrl).build();
        return retrofit.create(cls);
    }
}
