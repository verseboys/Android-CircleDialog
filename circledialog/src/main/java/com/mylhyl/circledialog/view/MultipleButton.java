package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
class MultipleButton extends ScaleLinearLayout {
    private CircleParams mCircleParams;
    private ButtonParams mNegativeParams;
    private ButtonParams mPositiveParams;
    private ButtonParams mNeutralParams;
    private ScaleTextView mNegativeButton;
    private ScaleTextView mPositiveButton;
    private ScaleTextView mNeutralButton;

    public MultipleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        setOrientation(HORIZONTAL);
        mCircleParams = params;
        mNegativeParams = params.negativeParams;
        mPositiveParams = params.positiveParams;
        mNeutralParams = params.neutralParams;

        int radius = params.dialogParams.radius;

        //取消按钮
        createNegative(radius);

        //分隔线
        createDivider();

        if (mNeutralParams != null) {
            createNeutral();
            createDivider();
        }

        //确定按钮
        createPositive(radius);
    }

    private void createDivider() {
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);
    }

    private void createNegative(int radius) {
        mNegativeButton = new ScaleTextView(getContext());
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(mNegativeParams.height);
        handleNegativeStyle();

        //如果取消按钮没有背景色，则使用默认色
        int backgroundNegative = mNegativeParams.backgroundColor != 0 ? mNegativeParams
                .backgroundColor : CircleColor.bgDialog;
        //按钮左下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNegativeButton.setBackground(new SelectorBtn(backgroundNegative, 0, 0, 0, radius));
        } else {
            mNegativeButton.setBackgroundDrawable(new SelectorBtn(backgroundNegative, 0, 0, 0,
                    radius));
        }

        regNegativeListener();

        addView(mNegativeButton);
    }

    private void handleNegativeStyle() {
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
    }

    private void createPositive(int radius) {

        mPositiveButton = new ScaleTextView(getContext());
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(mPositiveParams.height);
        handlePositiveStyle();

        //如果取消按钮没有背景色，则使用默认色
        int backgroundPositive = mPositiveParams.backgroundColor != 0 ? mPositiveParams
                .backgroundColor : CircleColor
                .bgDialog;

        //取消按钮右下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mPositiveButton.setBackground(new SelectorBtn(backgroundPositive, 0, 0, radius, 0));
        } else {
            mPositiveButton.setBackgroundDrawable(new SelectorBtn(backgroundPositive, 0, 0,
                    radius, 0));
        }

        regPositiveListener();

        addView(mPositiveButton);
    }

    private void handlePositiveStyle() {
        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
    }

    private void createNeutral() {

        mNeutralButton = new ScaleTextView(getContext());
        mNeutralButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(mNeutralParams.height);
        handleNeutralStyle();

        //如果取消按钮没有背景色，则使用默认色
        int backgroundPositive = mNeutralParams.backgroundColor != 0 ? mNeutralParams
                .backgroundColor : CircleColor.bgDialog;

        //取消按钮右下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNeutralButton.setBackground(new SelectorBtn(backgroundPositive, 0, 0, 0, 0));
        } else {
            mNeutralButton.setBackgroundDrawable(new SelectorBtn(backgroundPositive, 0, 0, 0, 0));
        }

        regNeutralListener();

        addView(mNeutralButton);
    }

    private void handleNeutralStyle() {
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
    }

    private void regNegativeListener() {
        mNegativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleParams.dismiss();
                if (mCircleParams.clickNegativeListener != null)
                    mCircleParams.clickNegativeListener.onClick(v);
            }
        });
    }

    private void regPositiveListener() {
        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleParams.dismiss();
                if (mCircleParams.clickPositiveListener != null)
                    mCircleParams.clickPositiveListener.onClick(v);
            }
        });
    }

    private void regNeutralListener() {
        mNeutralButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleParams.dismiss();
                if (mCircleParams.clickNeutralListener != null)
                    mCircleParams.clickNeutralListener.onClick(v);
            }
        });
    }

    public void regOnInputClickListener(final EditText input) {
        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (!TextUtils.isEmpty(text))
                    mCircleParams.dismiss();
                if (mCircleParams.inputListener != null)
                    mCircleParams.inputListener.onClick(text, v);
            }
        });
    }

    public void refreshText() {
        if (mNegativeParams == null || mNegativeButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNegativeStyle();
            }
        });

        if (mPositiveParams == null || mPositiveButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handlePositiveStyle();
            }
        });


        if (mNeutralParams == null || mNeutralButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNeutralStyle();
            }
        });
    }
}
