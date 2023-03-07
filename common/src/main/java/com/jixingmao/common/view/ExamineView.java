package com.jixingmao.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jixingmao.common.R;
import com.jixingmao.common.utils.ScreenUtils;

public class ExamineView extends View {

    private int mCircleSize;
    private int mColor;
    private boolean mIsLast = false;
    private Paint mPaint;

    public ExamineView(Context context) {
        super(context);
        initView(false);
    }

    public ExamineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
        initView(true);
    }

    public ExamineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);
        initView(true);
    }


    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.examineView);
        mCircleSize = typedArray.getDimensionPixelSize(R.styleable.examineView_circle_size, (int) ScreenUtils.dpToPx(10));
        mColor = typedArray.getColor(R.styleable.examineView_color, Color.parseColor("#3743A0"));
        mIsLast = typedArray.getBoolean(R.styleable.examineView_is_last, true);
        typedArray.recycle();
    }

    private void initView(boolean isInitObtainAttributes) {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }
        if (!isInitObtainAttributes) {
            mCircleSize = (int) ScreenUtils.dpToPx(10);
            mColor = Color.parseColor("#3743A0");
            mIsLast = false;
        }
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(ScreenUtils.dpToPx(2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //自定义的View能够使用wrap_content或者是match_parent的属性
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mIsLast) {
            mPaint.setColor(mColor);
        } else {
            mPaint.setColor(Color.parseColor("#BCC0E0"));
        }
        mPaint.setStyle(Paint.Style.FILL);
        int centerPoint = getWidth() / 2;
        canvas.drawCircle(centerPoint, mCircleSize, mCircleSize, mPaint);
        if (!mIsLast) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setPathEffect(new DashPathEffect(new float[]{ScreenUtils.dpToPx(5), ScreenUtils.dpToPx(5)}, 0));
            Path mPath = new Path();
            mPath.moveTo(centerPoint, mCircleSize * 2);
            mPath.lineTo(centerPoint, getHeight());
            canvas.drawPath(mPath, mPaint);
        }
    }

    public void setIsLast(boolean isLast) {
        this.mIsLast = isLast;
        invalidate();
    }
}
