package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 * 个人中心----考试中心
 */

public class MyTest extends BaseActivity {

    @BindView(R.id.mytest_back)
    ImageView testBack;
    @BindView(R.id.test_gridview)
    GridView testGridview;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_test;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {
        int netWorkType = NetworkUtil.getNetWorkType(mContext);
        if (netWorkType == 0){
            Toast.makeText(mContext,"网络无连接",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"请稍等",Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        testBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
