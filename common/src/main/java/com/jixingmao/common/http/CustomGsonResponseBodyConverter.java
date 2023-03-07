package com.jixingmao.common.http;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.jixingmao.common.http.bean.BasicResponse;
import com.jixingmao.common.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;


@Deprecated
final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String responseString = value.string();
            //打印后台数据
            LogUtils.e("===========responseString===========");
            LogUtils.e(responseString);
//            T response = adapter.fromJson(value.charStream());    要报错
            BasicResponse<T> response = gson.fromJson(responseString, BasicResponse.class);
            if (response.getCode() == 200) {
                //不能直接返回null 否则会报错
                if (response.getData() == null) {
                    return (T) new Object();
                }
                return response.getData();
            } else {
                // 特定 API 的错误，在相应的 DefaultObserver 的 onError 的方法中进行处理
                throw new ServiceException(response.getCode(), response.getMsg());
            }
        } finally {
            value.close();
        }
    }
}
