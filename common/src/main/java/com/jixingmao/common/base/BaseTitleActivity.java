package com.jixingmao.common.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jixingmao.common.R;


public abstract class BaseTitleActivity<T extends BasePresenter> extends BaseActivity<T> implements View.OnClickListener {


    protected TextView titleTv;
    protected ImageView backBt;
    protected ImageView action;
    protected TextView textAction;
    protected View line;
    protected FrameLayout topContent;
    protected FrameLayout topBarWithBottomLayout;
    protected View content;
    protected View defaultView;
    protected ImageView defaultIcon;
    protected TextView defaultTitle;
    protected TextView defaultBtn;
    protected boolean mCustomBackAction;
    protected ConstraintLayout topBar;
    protected ImageView lightBg;

    public abstract int getContentResId();

    public void clickAction() {

    }

    public void clickTextAction() {

    }


    @Override
    public void init() {
        topBar = findViewById(R.id.topBar);
        titleTv = findViewById(R.id.title);
        backBt = findViewById(R.id.back);
        action = findViewById(R.id.action);
        textAction = findViewById(R.id.text_action);
        line = findViewById(R.id.line);
        lightBg = findViewById(R.id.light_bg);

        topContent = findViewById(R.id.content);
        topBarWithBottomLayout = findViewById(R.id.topBarWithBottomLayout);
        content = LayoutInflater.from(this).inflate(getContentResId(), topContent, false);
        topContent.addView(content);
        defaultView = findViewById(R.id.default_view);
        defaultIcon = findViewById(R.id.default_icon);
        defaultTitle = findViewById(R.id.default_title);
        defaultBtn = findViewById(R.id.default_btn);

        backBt.setOnClickListener(this);
        action.setOnClickListener(this);
        textAction.setOnClickListener(this);
        defaultBtn.setOnClickListener(this);

        initView();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        if (ConfigStyle.getInstance().isShowLightBg()) {
            lightBg.setVisibility(View.VISIBLE);
        } else {
            lightBg.setVisibility(View.GONE);
        }
        if (ConfigStyle.getInstance().isLightBgImgColorInit()) {
            lightBg.setBackgroundColor(ConfigStyle.getInstance().getLightBgImgColor());
        }
        if (ConfigStyle.getInstance().isLightBgImgSrcInit()) {
            lightBg.setImageResource(ConfigStyle.getInstance().getLightBgImgSrc());
        }
        if (ConfigStyle.getInstance().isTopBarBgColorInit()) {
            topBar.setBackgroundColor(ConfigStyle.getInstance().getTopBarBgColor());
        }
        if (ConfigStyle.getInstance().isBackImgSrcInit()) {
            backBt.setImageResource(ConfigStyle.getInstance().getBackImgSrc());
        }
        if (ConfigStyle.getInstance().isTitleColorInit()) {
            titleTv.setTextColor(ConfigStyle.getInstance().getTitleColor());
        }
        if (ConfigStyle.getInstance().isTextActionColorInit()) {
            textAction.setTextColor(ConfigStyle.getInstance().getTextActionColor());
        }
        if (ConfigStyle.getInstance().isLineVisibilityInit()) {
            line.setVisibility(ConfigStyle.getInstance().getLineVisibility());
        }
        if (ConfigStyle.getInstance().isDefaultTextColorInit()) {
            defaultTitle.setTextColor(ConfigStyle.getInstance().getDefaultTextColor());
        }
        if (ConfigStyle.getInstance().isDefaultRetryBtnBgInit()) {
            defaultBtn.setBackgroundResource(ConfigStyle.getInstance().getDefaultRetryBtnBg());
        }
        if (ConfigStyle.getInstance().isDefaultRetryBtnTextColorInit()) {
            defaultBtn.setTextColor(ConfigStyle.getInstance().getDefaultRetryBtnTextColor());
        }
    }

    public void setLightModeTopBar() {
        if (ConfigStyle.getInstance().isTopBarLightBgColorInit()) {
            topBar.setBackgroundColor(ConfigStyle.getInstance().getTopBarLightBgColor());
        }
        if (ConfigStyle.getInstance().isTitleLightColorInit()) {
            titleTv.setTextColor(ConfigStyle.getInstance().getTitleLightColor());
        }
        if (ConfigStyle.getInstance().isBackLightImgSrcInit()) {
            backBt.setImageResource(ConfigStyle.getInstance().getBackLightImgSrc());
        }
        line.setVisibility(View.GONE);
        ImmersionBar.with(this)
                .transparentNavigationBar()
                .statusBarDarkFont(false)
                .init();
    }

    public void setDarkModeTopBar() {
        if (ConfigStyle.getInstance().isTopBarBgColorInit()) {
            topBar.setBackgroundColor(ConfigStyle.getInstance().getTopBarBgColor());
        }
        if (ConfigStyle.getInstance().isTitleColorInit()) {
            titleTv.setTextColor(ConfigStyle.getInstance().getTitleColor());
        }
        if (ConfigStyle.getInstance().isBackImgSrcInit()) {
            backBt.setImageResource(ConfigStyle.getInstance().getBackImgSrc());
        }
        line.setVisibility(View.VISIBLE);
        ImmersionBar.with(this)
                .transparentNavigationBar()
                .statusBarDarkFont(true)
                .init();
    }

    public void showLightBg() {
        lightBg.setVisibility(View.VISIBLE);
    }

    public void hideLightBg() {
        lightBg.setVisibility(View.GONE);
    }


    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setActionImgResource(int resId) {
        action.setVisibility(View.VISIBLE);
        textAction.setVisibility(View.GONE);
        action.setImageResource(resId);
    }

    public void setActionText(String text) {
        action.setVisibility(View.INVISIBLE);
        textAction.setVisibility(View.VISIBLE);
        textAction.setText(text);
    }

    public void setCustomBackAction(boolean customBackAction) {
        this.mCustomBackAction = customBackAction;
    }

    public void setTopBarWithBottomLayoutContent(View view) {
        if (topBarWithBottomLayout != null) {
            topBarWithBottomLayout.addView(view);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_title;
    }

    @Override
    public void onClick(View v) {
        if (!mCustomBackAction && v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.action) {
            clickAction();
        } else if (v.getId() == R.id.text_action) {
            clickTextAction();
        } else if (v.getId() == R.id.default_btn) {
            getPresenter().refreshData();
        }
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
        defaultTitle.setText(getString(R.string.page_404_text));
        if (ConfigStyle.getInstance().isNetWorkErrorImgSrcInit()) {
            defaultIcon.setImageResource(ConfigStyle.getInstance().getNetWorkErrorImgSrc());
        }
    }

    @Override
    public void showNoNetWorkErrorView() {
        defaultView.setVisibility(View.VISIBLE);
        defaultBtn.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        defaultTitle.setText(getString(R.string.page_no_network_text));
        if (ConfigStyle.getInstance().isNoNetWorkImgSrcInit()) {
            defaultIcon.setImageResource(ConfigStyle.getInstance().getNoNetWorkImgSrc());
        }
    }

    public void bindEmptyViewToAdapter(BaseQuickAdapter adapter, int resIcon, int resText) {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        ImageView emptyIcon = emptyView.findViewById(R.id.default_icon);
        TextView emptyTitle = emptyView.findViewById(R.id.default_title);
        emptyIcon.setImageResource(resIcon);
        emptyTitle.setText(resText);
        adapter.setEmptyView(emptyView);
        adapter.setHeaderWithEmptyEnable(true);
        adapter.setFooterWithEmptyEnable(true);
    }

    public static class ConfigStyle {
        private static ConfigStyle singleton;

        private boolean isShowLightBg;

        private int mLightBgImgSrc;
        private boolean mLightBgImgSrcInit;

        private int mLightBgImgColor;
        private boolean mLightBgImgColorInit;

        private int mTopBarBgColor;
        private boolean mTopBarBgColorInit;

        private int mTopBarLightBgColor;
        private boolean mTopBarLightBgColorInit;

        private int mBackImgSrc;
        private boolean mBackImgSrcInit;

        private int mBackLightImgSrc;
        private boolean mBackLightImgSrcInit;

        private int mTitleColor;
        private boolean mTitleColorInit;

        private int mTitleLightColor;
        private boolean mTitleLightColorInit;

        private int mTextActionColor;
        private boolean mTextActionColorInit;

        private int mLineVisibility;
        private boolean mLineVisibilityInit;

        private int mDefaultTextColor;
        private boolean mDefaultTextColorInit;

        private int mDefaultRetryBtnTextColor;
        private boolean mDefaultRetryBtnTextColorInit;

        private int mDefaultRetryBtnBg;
        private boolean mDefaultRetryBtnBgInit;

        private int mNetWorkErrorImgSrc;
        private boolean mNetWorkErrorImgSrcInit;

        private int mNoNetWorkImgSrc;
        private boolean mNoNetWorkImgSrcInit;


        public static ConfigStyle getInstance() {
            if (singleton == null) {
                synchronized (ConfigStyle.class) {
                    if (singleton == null) {
                        singleton = new ConfigStyle();
                    }
                }
            }
            return singleton;
        }

        public boolean isShowLightBg() {
            return isShowLightBg;
        }

        public void setShowLightBg(boolean showLightBg) {
            isShowLightBg = showLightBg;
        }

        public int getTopBarLightBgColor() {
            return mTopBarLightBgColor;
        }

        public void setTopBarLightBgColor(int mTopBarLightBgColor) {
            this.mTopBarLightBgColor = mTopBarLightBgColor;
            this.mTopBarLightBgColorInit = true;
        }

        public boolean isTopBarLightBgColorInit() {
            return mTopBarLightBgColorInit;
        }

        public int getBackLightImgSrc() {
            return mBackLightImgSrc;
        }

        public void setBackLightImgSrc(int mBackLightImgSrc) {
            this.mBackLightImgSrc = mBackLightImgSrc;
            this.mBackLightImgSrcInit = true;
        }

        public boolean isBackLightImgSrcInit() {
            return mBackLightImgSrcInit;
        }

        public int getTitleLightColor() {
            return mTitleLightColor;
        }

        public void setTitleLightColor(int mTitleLightColor) {
            this.mTitleLightColor = mTitleLightColor;
            this.mTitleLightColorInit = true;
        }

        public boolean isTitleLightColorInit() {
            return mTitleLightColorInit;
        }

        public int getLightBgImgSrc() {
            return mLightBgImgSrc;
        }

        public void setLightBgImgSrc(int mLightBgImgSrc) {
            this.mLightBgImgSrc = mLightBgImgSrc;
            this.mLightBgImgSrcInit = true;
        }

        public boolean isLightBgImgSrcInit() {
            return mLightBgImgSrcInit;
        }

        public int getLightBgImgColor() {
            return mLightBgImgColor;
        }

        public void setLightBgImgColor(int mLightBgImgColor) {
            this.mLightBgImgColor = mLightBgImgColor;
            this.mLightBgImgColorInit = true;
        }

        public boolean isLightBgImgColorInit() {
            return mLightBgImgColorInit;
        }

        public int getTopBarBgColor() {
            return mTopBarBgColor;
        }

        public void setTopBarBgColor(int mTopBarBgColor) {
            this.mTopBarBgColor = mTopBarBgColor;
            this.mTopBarBgColorInit = true;
        }

        public boolean isTopBarBgColorInit() {
            return mTopBarBgColorInit;
        }

        public int getBackImgSrc() {
            return mBackImgSrc;
        }

        public void setBackImgSrc(int mBackImgSrc) {
            this.mBackImgSrc = mBackImgSrc;
            this.mBackImgSrcInit = true;
        }

        public boolean isBackImgSrcInit() {
            return mBackImgSrcInit;
        }

        public int getTitleColor() {
            return mTitleColor;
        }

        public void setTitleColor(int mTitleColor) {
            this.mTitleColor = mTitleColor;
            this.mTitleColorInit = true;
        }

        public boolean isTitleColorInit() {
            return mTitleColorInit;
        }

        public int getTextActionColor() {
            return mTextActionColor;
        }

        public void setTextActionColor(int mTextActionColor) {
            this.mTextActionColor = mTextActionColor;
            this.mTextActionColorInit = true;
        }

        public boolean isTextActionColorInit() {
            return mTextActionColorInit;
        }

        public int getLineVisibility() {
            return mLineVisibility;
        }

        public void setLineVisibility(int mLineVisibility) {
            this.mLineVisibility = mLineVisibility;
            this.mLineVisibilityInit = true;
        }

        public boolean isLineVisibilityInit() {
            return mLineVisibilityInit;
        }

        public int getDefaultTextColor() {
            return mDefaultTextColor;
        }

        public void setDefaultTextColor(int mDefaultTextColor) {
            this.mDefaultTextColor = mDefaultTextColor;
            this.mDefaultTextColorInit = true;
        }

        public boolean isDefaultTextColorInit() {
            return mDefaultTextColorInit;
        }

        public int getDefaultRetryBtnTextColor() {
            return mDefaultRetryBtnTextColor;
        }

        public void setDefaultRetryBtnTextColor(int mDefaultRetryBtnTextColor) {
            this.mDefaultRetryBtnTextColor = mDefaultRetryBtnTextColor;
            this.mDefaultRetryBtnTextColorInit = true;
        }

        public boolean isDefaultRetryBtnTextColorInit() {
            return mDefaultRetryBtnTextColorInit;
        }

        public int getDefaultRetryBtnBg() {
            return mDefaultRetryBtnBg;
        }

        public void setDefaultRetryBtnBg(int mDefaultRetryBtnBg) {
            this.mDefaultRetryBtnBg = mDefaultRetryBtnBg;
            this.mDefaultRetryBtnBgInit = true;
        }

        public boolean isDefaultRetryBtnBgInit() {
            return mDefaultRetryBtnBgInit;
        }

        public int getNetWorkErrorImgSrc() {
            return mNetWorkErrorImgSrc;
        }

        public void setNetWorkErrorImgSrc(int mNetWorkErrorImgSrc) {
            this.mNetWorkErrorImgSrc = mNetWorkErrorImgSrc;
            this.mNetWorkErrorImgSrcInit = true;
        }

        public boolean isNetWorkErrorImgSrcInit() {
            return mNetWorkErrorImgSrcInit;
        }

        public int getNoNetWorkImgSrc() {
            return mNoNetWorkImgSrc;
        }

        public void setNoNetWorkImgSrc(int mNoNetWorkImgSrc) {
            this.mNoNetWorkImgSrc = mNoNetWorkImgSrc;
            this.mNoNetWorkImgSrcInit = true;
        }


        public boolean isNoNetWorkImgSrcInit() {
            return mNoNetWorkImgSrcInit;
        }
    }
}
