package com.jixingmao.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jixingmao.common.R;

public abstract class BaseTitleWithHeaderActivity extends BaseActivity implements View.OnClickListener {


    protected TextView titleTv;
    protected ImageView backBt;
    protected FrameLayout topContent;
    protected View content;

    public abstract int getContentResId();


    @Override
    public void init() {
        titleTv = findViewById(R.id.title);
        backBt = findViewById(R.id.back);
        topContent = findViewById(R.id.content);
        content = LayoutInflater.from(this).inflate(getContentResId(), topContent, false);
        topContent.addView(content);
        backBt.setOnClickListener(this);
    }

    public void setTitle(String title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_title;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
    }
}
