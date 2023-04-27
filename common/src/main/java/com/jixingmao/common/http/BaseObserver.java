package com.jixingmao.common.http;

import com.google.gson.JsonParseException;
import com.jixingmao.common.R;
import com.jixingmao.common.base.IView;
import com.jixingmao.common.event.TokenExpiredEvent;
import com.jixingmao.common.http.bean.BasicResponse;
import com.jixingmao.common.utils.LogUtils;
import com.jixingmao.common.utils.NetWorkUtils;
import com.jixingmao.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T extends BasicResponse<D>, D> implements Observer<T> {

    public static final int HTTP_SUCCESS_CODE = 200;


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        if (response.getCode() == HTTP_SUCCESS_CODE) {
            onSuccess((D) response.getData());
            onFinish();
        } else {
            onError(new ServiceException(response.getCode(), response.getMsg()));
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("=================Retrofit onError================= \n" + e);
        
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else if (e instanceof ServiceException) {
            if (((ServiceException) e).getCode() == 401) {
                //Token无效
                EventBus.getDefault().post(new TokenExpiredEvent());
            }
            onFail(((ServiceException) e).getMsg());
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {
        LogUtils.e("=================onComplete================= \n");
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(D response);

    /**
     * 服务器返回数据，但响应码不为200
     */
    public void onFail(String message) {
        ToastUtils.show(message);
    }

    public void onFinish() {
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(R.string.connect_error);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(R.string.connect_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.show(R.string.bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.show(R.string.parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(R.string.unknown_error);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}