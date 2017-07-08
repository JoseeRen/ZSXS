package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/7/1.
 * 从点击条目事件把数据传递过来消息中心页面
 */

public class UserMessageCenter extends BaseActivity {
    @BindView(R.id.iv_usermessage_back)
    ImageView ivUsermessageBack;
    @BindView(R.id.tv_messagecenter_title)
    TextView tvMessagecenterTitle;
    @BindView(R.id.tv_messagecenter_content)
    TextView tvMessagecenterContent;
    @BindView(R.id.tv_messagecenter_time)
    TextView tvMessagecenterTime;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_usermessagecenter;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        // 获取点击条目传过来的数据
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        tvMessagecenterTitle.setText(title);
        String content = intent.getStringExtra("content");
        tvMessagecenterContent.setText(content);
        String time = intent.getStringExtra("time");
        tvMessagecenterTime.setText(time);

        // 返回按钮点击事件
        ivUsermessageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
