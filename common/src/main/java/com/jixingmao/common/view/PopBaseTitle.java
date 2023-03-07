package com.jixingmao.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jixingmao.common.R;

public class PopBaseTitle extends PopupWindow {

    private View content;

    private Activity mContext;

    private TextView title;
    private TextView sure;
    private TextView cancel;

    public PopBaseTitle(final Activity context) {
        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        content = inflater.inflate(R.layout.pop_base_title, null);
        title = content.findViewById(R.id.title);
        sure = content.findViewById(R.id.sure);
        cancel = content.findViewById(R.id.cancel);

        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setSureText(String text) {
        sure.setText(text);
    }

    public void setCancelText(String text) {
        cancel.setText(text);
    }

    public void setSureClickListener(View.OnClickListener onClickListener) {
        sure.setOnClickListener(onClickListener);
    }

    public void setCancelClickListener(View.OnClickListener onClickListener) {
        cancel.setOnClickListener(onClickListener);
    }
}
