package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;

import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.CourseListBean;

/**
 * Created by Mr_Shadow on 2017/6/25.
 * 音频中心点击条目进去的音频详情
 */

public class AudioCenterDetailActivity extends BaseActivity {
    @Override
    public int getContentViewResId() {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //获取到的课程详细
        CourseListBean.CourseBean data = (CourseListBean.CourseBean) bundle.getSerializable("data");



    }
}
