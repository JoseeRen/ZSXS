/*
 * Create on 2017-6-27 下午3:13
 * FileName: Comment_Fragment.java
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
 * 视频播放页面的评论
 */

public class Comment_Fragment extends BaseFragment {
    static Comment_Fragment instance;

    public static Comment_Fragment getInstance() {
        if (instance == null) {
            instance = new Comment_Fragment();
        }
        return instance;
    }
    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment;
    }
}
