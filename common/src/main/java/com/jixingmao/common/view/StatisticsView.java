package com.jixingmao.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jixingmao.common.R;
import com.jixingmao.common.utils.LogUtils;
import com.jixingmao.common.utils.ScreenUtils;

public class StatisticsView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private LinearLayout mContent;
    private TextView mMoney;
    private View progress;
    private View mLine;
    private TextView mTips;
    private int mColor;
    private int mSelectedColor;
    private int mMaxHeight;

    int measuredHeight;
    private int mMoneyMeasuredHeight;
    private int mLineMeasuredHeight;
    private int mTipsMeasuredHeight;
    private int mMaxProgressHeight;
    private int mChildMarginParams;

    private double mMaxProgress = 100;
    private double mCurrentProgress = 0;

    public StatisticsView(Context context) {
        super(context);
        initView();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
        initView();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);
        initView();
    }

    private void initView() {
        mContent = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_statistics, null);
        addView(mContent);
        mMoney = mContent.findViewById(R.id.money);
        progress = mContent.findViewById(R.id.progress);
        mLine = mContent.findViewById(R.id.line);
        mTips = mContent.findViewById(R.id.tip);
        mColor = getContext().getResources().getColor(R.color.app_theme_color_light);
        mSelectedColor = getContext().getResources().getColor(R.color.app_theme_color);

        mContent.setOnClickListener(v -> {
            setSelected(!isSelected());
            int currentColor = isSelected() ? mSelectedColor : mColor;
            mMoney.setTextColor(currentColor);
            progress.setBackgroundColor(currentColor);
            mLine.setBackgroundColor(currentColor);
            mTips.setTextColor(currentColor);
        });
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.statisticsView);
        mMaxHeight = (int) typedArray.getDimension(R.styleable.statisticsView_max_height, (int) ScreenUtils.dpToPx(200));
        typedArray.recycle();
        mProcessLayout();
    }

    private void mProcessLayout() {
        if (mMaxHeight <= 0) {
            mMaxHeight = (int) ScreenUtils.dpToPx(200);
        }
        mChildMarginParams = (int) ScreenUtils.dpToPx(11);
    }

    public void setMaxHeight(int maxHeightDp) {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        mMaxHeight = (int) ScreenUtils.dpToPx(maxHeightDp);
        requestLayout();
    }

    public void setMaxProgress(double progress) {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.mMaxProgress = progress;
        requestLayout();
    }

    public void setCurrentProgress(double progress) {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.mCurrentProgress = progress;
        if (mCurrentProgress >= mMaxProgress) {
            mCurrentProgress = mMaxProgress;
        }
        requestLayout();
    }

    public void setMoney(String money) {
        mMoney.setText(money);
    }

    public void setTips(String tips) {
        mTips.setText(tips);
    }

    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        measuredHeight = getMeasuredHeight();
        mMoneyMeasuredHeight = mMoney.getMeasuredHeight();
        mLineMeasuredHeight = mLine.getMeasuredHeight();
        mTipsMeasuredHeight = mTips.getMeasuredHeight();

        mMaxProgressHeight = mMaxHeight - (mMoneyMeasuredHeight + mLineMeasuredHeight + mTipsMeasuredHeight + mChildMarginParams);
        LogUtils.e("measuredHeight:" + measuredHeight + "\nmMoneyMeasuredHeight" + mMoneyMeasuredHeight
                + "\nmLineMeasuredHeight" + mLineMeasuredHeight
                + "\nmTipsMeasuredHeight" + mTipsMeasuredHeight
                + "\nmMaxProgressHeight" + mMaxProgressHeight
                + "\nmMaxHeight" + mMaxHeight

        );
        reLayoutProgressView();
    }

    private void reLayoutProgressView() {
        int computerHeight;
        float rate = (float) mCurrentProgress / (float) mMaxProgress;
        computerHeight = (int) (mMaxProgressHeight * rate);
        LogUtils.e(computerHeight + ": computerHeight");
        ViewGroup.LayoutParams layoutParams = this.progress.getLayoutParams();
        layoutParams.height = computerHeight;
        this.progress.setLayoutParams(layoutParams);
    }


}
