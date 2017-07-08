package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/7/2.
 */

public class MyErweimaActivity extends BaseActivity {
    @BindView(R.id.iv_myerweima_back)
    ImageView ivMyerweimaBack;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_myerweima;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ivMyerweimaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
