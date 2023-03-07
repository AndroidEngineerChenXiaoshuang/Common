package com.jixingmao.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jixingmao.common.R;
import com.jixingmao.common.event.DoSomethingEvent;
import com.jixingmao.common.utils.ToastUtils;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle4.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

abstract public class BaseFragment<T extends BasePresenterFragment> extends RxFragment implements View.OnClickListener, BaseViewFragment {

    public abstract int getLayoutResId();

    public abstract void init();

    private T mPresenter;

    private View defaultView;
    private ImageView defaultIcon;
    private TextView defaultTitle;
    private TextView defaultBtn;
    private View content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).init();
        ViewGroup topContent = (ViewGroup) inflater.inflate(R.layout.fragment_base, container, false);
        content = inflater.inflate(getLayoutResId(), container, false);
        topContent.addView(content);
        defaultView = topContent.findViewById(R.id.default_view);
        defaultIcon = topContent.findViewById(R.id.default_icon);
        defaultTitle = topContent.findViewById(R.id.default_title);
        defaultBtn = topContent.findViewById(R.id.default_btn);
        defaultBtn.setOnClickListener(this);
        return topContent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ImmersionBar.with(this)
                .transparentNavigationBar()
                .statusBarDarkFont(true)
                .init();
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        //移除所有粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //解除注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doSomething(DoSomethingEvent event) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.default_btn) {
            getPresenter().refreshData();
        }
    }

    public void setPresenter(T mPresenter) {
        this.mPresenter = mPresenter;
    }

    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public RxFragment getRxFragment() {
        return this;
    }

    @Override
    public void hideDefaultView() {
        if (defaultView.getVisibility() == View.VISIBLE) {
            defaultView.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNetWorkErrorView() {
        defaultView.setVisibility(View.VISIBLE);
        defaultBtn.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        defaultIcon.setImageResource(R.mipmap.page_404);
        defaultTitle.setText(getString(R.string.page_404_text));
    }

    @Override
    public void showNoNetWorkErrorView() {
        defaultView.setVisibility(View.VISIBLE);
        defaultBtn.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        defaultIcon.setImageResource(R.mipmap.page_no_network);
        defaultTitle.setText(getString(R.string.page_no_network_text));
    }

    public void bindEmptyViewToAdapter(BaseQuickAdapter adapter, int resIcon, int resText) {
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        ImageView emptyIcon = emptyView.findViewById(R.id.default_icon);
        TextView emptyTitle = emptyView.findViewById(R.id.default_title);
        emptyIcon.setImageResource(resIcon);
        emptyTitle.setText(resText);
        adapter.setEmptyView(emptyView);
        adapter.setHeaderWithEmptyEnable(true);
        adapter.setFooterWithEmptyEnable(true);
    }
}
