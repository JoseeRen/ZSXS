/*
 * Create on 2017-6-27 下午3:13
 * FileName: Comment_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面的评论
 */

public class Comment_Fragment extends BaseFragment {
    static Comment_Fragment instance;
    @BindView(R.id.lv_comment_fragment)
    PullToRefreshListView lvCommentFragment;
    @BindView(R.id.ll_empty_view_comment)
    LinearLayout llEmptyViewComment;
    @BindView(R.id.et_comment_fragment)
    EditText etCommentFragment;
    @BindView(R.id.send_comment_fragment)
    Button sendCommentFragment;
    Unbinder unbinder;

    public static Comment_Fragment getInstance() {
        if (instance == null) {
            instance = new Comment_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//TODO   条目 的item   分页请求网络
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment;
    }


    @OnClick(R.id.send_comment_fragment)
    public void onViewClicked() {
    }
}
