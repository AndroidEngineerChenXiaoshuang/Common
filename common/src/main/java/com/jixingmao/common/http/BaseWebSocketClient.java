package com.jixingmao.common.http;

import com.jixingmao.common.utils.DateUtils;
import com.jixingmao.common.utils.LogUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BaseWebSocketClient extends WebSocketClient {

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
        LogUtils.i("onClose time: " + DateUtils.getCurrentDate());
        LogUtils.i("onClose info：" + code + " " + reason + " " + remote);
    }

    @Override
    public void onError(Exception ex) {
        LogUtils.e("onError at time：" + DateUtils.getCurrentDate());
        LogUtils.e("onError info：" + ex);
    }
}
