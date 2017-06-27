/*
 * Create on 2017-6-8 下午5:33
 * FileName: BaseFragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryw.zsxs.app.MyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public abstract class BaseFragment extends Fragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    private View mRootView;
    protected Context mContext= MyApplication.getInstance();
    public Unbinder mUnbinder;
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;    }

    public abstract void init(Bundle savedInstanceState);

    public abstract int getLayoutResId();

    @Override
    public final  void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * Activity跳转
     * @param
     * @param targetActivity
     * @param bundle
     */
    public void startActivity(Class<?> targetActivity,Bundle bundle){
        Intent intent = new Intent(getActivity(), targetActivity);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
