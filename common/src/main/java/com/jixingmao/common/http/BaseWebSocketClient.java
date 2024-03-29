package com.jixingmao.common.http;

import com.jixingmao.common.utils.DateUtils;
import com.jixingmao.common.utils.LogUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseWebSocketClient extends WebSocketClient {

    protected boolean isReConnect;

    public BaseWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        LogUtils.i("server status：" + handShakeData.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
        LogUtils.i("receive message：" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtils.i("onClose time: " + new Date());
        LogUtils.i("onClose info：" + code + " " + reason + " " + remote);
        if (isReConnect) {
            Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Throwable {
                            LogUtils.i("reconnect ....");
                            reconnect();
                        }
                    });
        }
    }

    @Override
    public void onError(Exception ex) {
        LogUtils.e("onError at time：" + DateUtils.getCurrentDate());
        LogUtils.e("onError info：" + ex);
    }

}
