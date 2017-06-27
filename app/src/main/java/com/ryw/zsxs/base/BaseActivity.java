/*
 * Create on 2017-6-8 下午4:50
 * FileName: BaseActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public abstract class BaseActivity extends Activity {
    private Unbinder mUnbinder;
    protected static String TAG;
    protected Context mContext;
    private Toast toastUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        mUnbinder = ButterKnife.bind(this);
        TAG = this.getClass().getSimpleName();
        mContext = this;
        init(savedInstanceState);
    }

    /**
     * 返回布局文件
     *
     * @return
     */
    public abstract int getContentViewResId();

    /**
     * 这个方法完成onCreate方法中的操作
     *
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /**
     * Activity跳转
     * @param
     * @param targetActivity
     * @param bundle
     */
    public void startActivity(Class<?> targetActivity,Bundle bundle){
        Intent intent = new Intent(this, targetActivity);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //弹吐司
    public void showToast(String toast) {

        if (toastUtil == null) {
            toastUtil = new Toast(this);
        }
        toastUtil.makeText(this, toast, Toast.LENGTH_SHORT).show();


    }
}
