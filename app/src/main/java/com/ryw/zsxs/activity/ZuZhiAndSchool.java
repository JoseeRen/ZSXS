package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.NetworkUtil;

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
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };*/
        int netWorkType = NetworkUtil.getNetWorkType(mContext);
        if (netWorkType == 0){
            Toast.makeText(mContext,"网络没有连接",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"请稍等",Toast.LENGTH_SHORT).show();
        }
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
