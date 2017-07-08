/*
 * Create on 2017-7-3 上午11:48
 * FileName: CustomDialog.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ryw.zsxs.R;

/**
 * Created by Mr_Shadow on 2017/7/3.
 * 自定义对话框
 */

public abstract class CustomDialog extends Dialog implements View.OnClickListener {
    private boolean cancelable = false;
    public Context context;
    private boolean outSideCancelable = false;
    protected View view;

    protected CustomDialog(@NonNull Context context, boolean cancelable, boolean outSideCancelable) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.cancelable = cancelable;
        this.outSideCancelable = outSideCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

        this.view = setView(LayoutInflater.from(this.context));
        setContentView(view);
        Window dialogWindow = getWindow();

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (((float) this.context.getResources().getDisplayMetrics().widthPixels) * widthPercent());
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(this.outSideCancelable);
        setCancelable(this.cancelable);
        initData();

    }

    ;

    protected float widthPercent() {
        return 0.8f;
    }

    public void dismiss() {
        super.dismiss();
        DialogManager.setNull();
    }

    public interface OnItemClickListener {
        void firstClick();

        void secondClick();
    }

    public abstract void initData();

    public abstract View setView(LayoutInflater layoutInflater);

    @Override
    public void onClick(View view) {

    }
}
