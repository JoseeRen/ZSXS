/*
 * Create on 2017-7-3 上午11:59
 * FileName: DialogManager.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.dialog;

import android.app.Activity;

/**
 * Created by Mr_Shadow on 2017/7/3.
 */

public class DialogManager {

    private static CustomDialog mCustomDialog;

    private DialogManager() {
    }

    public static void show(CustomDialog customDialog) {
        if (mCustomDialog == null) {
            mCustomDialog = customDialog;
        }
        if (!((Activity) mCustomDialog.context).isFinishing()) {
            mCustomDialog.show();
        }
    }

    public static boolean isNull() {
        return mCustomDialog == null;
    }

    public static void dismiss() {
        if (mCustomDialog != null) {
            mCustomDialog.dismiss();
            mCustomDialog = null;
        }
    }

    public static void setNull() {
        if (mCustomDialog != null) {
            mCustomDialog = null;
        }
    }

}
