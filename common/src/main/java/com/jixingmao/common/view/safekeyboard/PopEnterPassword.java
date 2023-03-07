package com.jixingmao.common.view.safekeyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jixingmao.common.R;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;

    private OnPasswordInputFinish onPasswordInputFinish;

    public PopEnterPassword(final Activity context) {

        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = mMenuView.findViewById(R.id.pwd_view);

        //添加密码输入完成的响应
        pwdView.setOnFinishInput(password -> {
            if (onPasswordInputFinish != null) {
                onPasswordInputFinish.inputFinish(password);
            }
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(v -> dismiss());

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }

    public void setOnPasswordInputFinish(OnPasswordInputFinish onPasswordInputFinish) {
        this.onPasswordInputFinish = onPasswordInputFinish;
    }

    public void setAmount(String amount) {
        pwdView.textAmount.setText(amount);
    }

    public void setServiceRate(String serviceRate) {
        pwdView.service_rate.setText(serviceRate);
    }

    public void setEmail(String email) {
        pwdView.email.setText(email);
    }
}
