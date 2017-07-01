/*
 * Create on 2017-6-27 下午3:11
 * FileName: Details_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseFragment;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面详情
 */

public class Details_Fragment extends BaseFragment {
    static Details_Fragment instance;

    public static Details_Fragment getInstance() {
        if (instance == null) {
            instance = new Details_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_details;
    }
}
