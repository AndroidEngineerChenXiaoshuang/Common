package com.jixingmao.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.jixingmao.common.R;
import com.jixingmao.common.event.DoSomethingEvent;
import com.jixingmao.common.utils.SharedPreferencesUtils;
import com.jixingmao.common.utils.ToastUtils;
import com.jixingmao.common.utils.language.LanguageUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

abstract public class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    public abstract int getLayoutResId();

    public abstract void init();

    private T mPresenter;


    @Override
    protected void attachBaseContext(Context newBase) {
        String language = SharedPreferencesUtils.getString(newBase,
                LanguageUtil.SP_KEY_LANGUAGE, LanguageUtil.SIMPLIFIED_CHINESE);
        //切换多语言，然后将新生成的 context 覆盖给 attachBaseContext()
        Context context = LanguageUtil.attachBaseContext(newBase, language);
        //兼容appcompat 1.2.0后切换语言失效问题
        final Configuration configuration = context.getResources().getConfiguration();
        final ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,
                R.style.Base_Theme_AppCompat_Empty) {
            @Override
            public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                if (overrideConfiguration != null) {
                    overrideConfiguration.setTo(configuration);
                }
                super.applyOverrideConfiguration(overrideConfiguration);
            }
        };
        super.attachBaseContext(wrappedContext);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fullScreen(false)
                .statusBarDarkFont(true)
                .init();
        EventBus.getDefault().register(this);
        setContentView(getLayoutResId());
        init();
    }

    @Override
    protected void onDestroy() {
        //移除所有粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //解除注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doSomething(DoSomethingEvent event) {
        if (event.getType() == DoSomethingEvent.EXIT_TYPE) {
            finish();
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
    public RxAppCompatActivity getRxActivity() {
        return this;
    }

    public void back() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void changeLanguage(String newLanguage, Class homeClass) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(this, newLanguage);
        }
        SharedPreferencesUtils.saveString(this, LanguageUtil.SP_KEY_LANGUAGE, newLanguage);
        Intent intent = new Intent(this, homeClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
