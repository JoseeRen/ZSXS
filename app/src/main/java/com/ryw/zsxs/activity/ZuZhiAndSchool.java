package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 * 个人中心------组织和学校
 */

public class ZuZhiAndSchool extends BaseActivity {

    @BindView(R.id.zuzhiAndxuexiao_back)
    ImageView zuzhiAndxuexiaoBack;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_zuzhiangxuexiao;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initData();
        initEvent();
    }

    private void initData() {

    }

    private void initEvent() {

        zuzhiAndxuexiaoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




}
