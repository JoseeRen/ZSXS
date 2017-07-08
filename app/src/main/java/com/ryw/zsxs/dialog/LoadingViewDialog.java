/*
 * Create on 2017-7-3 下午12:00
 * FileName: LoadingViewDialog.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryw.zsxs.R;

/**
 * Created by Mr_Shadow on 2017/7/3.
 */

public class LoadingViewDialog extends CustomDialog {
    private MyOnCancelListener onCancelListener;

    private  String text;
    protected LoadingViewDialog(@NonNull Context context, String text, @Nullable MyOnCancelListener cancelListener) {

        super(context, false, false);
        this.text=text;
        this.onCancelListener=cancelListener;
    }

    @Override
    public void onClick(View view) {
        DialogManager.dismiss();
        if (this.onCancelListener!=null){
            this.onCancelListener.cancel();
        }
    }

    @Override
    public void initData() {
        TextView textView = this.view.findViewById(R.id.textView1);
        if (this.text!=null){
            textView.setText(this.text);
        }
        ((Button) this.view.findViewById(R.id.cancel_btn)).setOnClickListener(this);

    }

    @Override
    public View setView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.loading_view,null);
    }
}
