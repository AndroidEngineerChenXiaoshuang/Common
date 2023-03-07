package com.jixingmao.common.base;

import com.jixingmao.common.http.ProgressUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasePresenterFragment<T extends BaseViewFragment> {
    private final T mView;

    public BasePresenterFragment(T view) {
        this.mView = view;
    }

    public T getView() {
        return mView;
    }

    protected int currentPageNo = 1;

    protected final int pageSize = 10;


    public void refreshData() {

    }

    public void loadMoreData() {

    }

    //compose操作符是用来对Observable进行转换操作的，并且可以保证调用链不被破坏。
    public <T> ObservableTransformer<T, T> basicTransform() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(getView().getRxFragment().bindToLifecycle())
                        .compose(ProgressUtils.applyProgressBar(getView().getRxFragment().getActivity()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public <T> ObservableTransformer<T, T> basicTransformNoProgress() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(getView().getRxFragment().bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
