package com.jixingmao.common.base;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

public interface BaseView extends IView {
    void showToast(String msg);

    RxAppCompatActivity getRxActivity();
}

