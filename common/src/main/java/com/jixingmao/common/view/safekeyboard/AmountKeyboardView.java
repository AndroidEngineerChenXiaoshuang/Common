package com.jixingmao.common.view.safekeyboard;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.jixingmao.common.R;

/**
 * 虚拟键盘
 */
public class AmountKeyboardView extends FrameLayout implements View.OnClickListener {

    Context context;

    private Activity mActivity;

    private EditText textAmount;

    private Animation enterAnim;

    private Animation exitAnim;

    private TextView num0;
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView num4;
    private TextView num5;
    private TextView num6;
    private TextView num7;
    private TextView num8;
    private TextView num9;
    private TextView numPoint;
    private FrameLayout delete;
    private TextView submit;

    public OnSubmitClickListener onSubmitClickListener;

    public interface OnSubmitClickListener {
        void onClickSubmit();
    }

    public AmountKeyboardView(Context context) {
        this(context, null);
    }

    public AmountKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.layout_amount_keyboard, null);

        num0 = view.findViewById(R.id.num0);
        num1 = view.findViewById(R.id.num1);
        num2 = view.findViewById(R.id.num2);
        num3 = view.findViewById(R.id.num3);
        num4 = view.findViewById(R.id.num4);
        num5 = view.findViewById(R.id.num5);
        num6 = view.findViewById(R.id.num6);
        num7 = view.findViewById(R.id.num7);
        num8 = view.findViewById(R.id.num8);
        num9 = view.findViewById(R.id.num9);
        numPoint = view.findViewById(R.id.num_point);
        delete = view.findViewById(R.id.delete);
        submit = view.findViewById(R.id.submit);


        addView(view);      //必须要，不然不显示控件
    }


    public void init(Activity activity, EditText editText) {
        mActivity = activity;
        textAmount = editText;
        initAnim();
        initView();
    }

    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_out);
    }

    private void initView() {
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        numPoint.setOnClickListener(this);
        delete.setOnClickListener(this);
        submit.setOnClickListener(this);


        textAmount.setShowSoftInputOnFocus(false);
        textAmount.setOnClickListener(v -> {
            startAnimation(enterAnim);
            setVisibility(View.VISIBLE);
        });
        textAmount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                //自定义键盘输入框获取焦点的时候隐藏系统软键盘并展示我们自己的键盘
                hideSoftKeyboard();
                setFocusable(true);
                setFocusableInTouchMode(true);
                startAnimation(enterAnim);
                setVisibility(View.VISIBLE);
            } else {
                //失去焦点的时候把我们的键盘也隐藏掉
                setVisibility(View.GONE);
                startAnimation(exitAnim);
            }
        });
    }

    public void hideSoftKeyboard() {
        if (mActivity != null) {
            View view = mActivity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void setOnSubmitClickListener(OnSubmitClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.num1 || id == R.id.num2 || id == R.id.num3 ||
                id == R.id.num4 || id == R.id.num5 || id == R.id.num6
                || id == R.id.num7 || id == R.id.num8 || id == R.id.num9
                || id == R.id.num0) {
            String amount = textAmount.getText().toString().trim();
            if (amount.contains(".")) {
                String[] args = amount.split("\\.");
                if (args.length >= 2 && args[1].length() >= 2) {
                    return;
                }
            }
            amount = amount + ((TextView) v).getText();
            textAmount.setText(amount);
            Editable ea = textAmount.getText();
            textAmount.setSelection(ea.length());
        } else if (id == R.id.num_point) {
            String amount = textAmount.getText().toString().trim();
            if (!TextUtils.isEmpty(amount) && !amount.contains(".")) {
                amount = amount + ".";
                textAmount.setText(amount);
                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            }
        } else if (id == R.id.delete) {
            String amount = textAmount.getText().toString().trim();
            if (amount.length() > 0) {
                amount = amount.substring(0, amount.length() - 1);
                textAmount.setText(amount);

                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            }
        } else if (id == R.id.submit && onSubmitClickListener != null) {
            onSubmitClickListener.onClickSubmit();
        }
    }
}
