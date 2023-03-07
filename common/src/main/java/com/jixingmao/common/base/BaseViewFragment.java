package com.jixingmao.common.base;

import com.trello.rxlifecycle4.components.support.RxFragment;

public interface BaseViewFragment extends IView {
    void showToast(String msg);

    RxFragment getRxFragment();
}
