package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/24.
 * 个人信息页面 邮箱页面
 */

public class UserEmailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_email_back)
    ImageView ivEmailBack;
    @BindView(R.id.bt_email_save)
    Button btEmailSave;
    @BindView(R.id.et_email)
    EditText etEmail;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_useremail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {
        // 获取传过来的数据（昵称）
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");
        etEmail.setText(userEmail.toString());

    }

    private void initEvent() {
        ivEmailBack.setOnClickListener(this);
        btEmailSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_email_back:
                finish();
                break;
            case R.id.bt_email_save:
                // 把修改的Email提交到服务器上；
                HashMap<String, String> hashmap = new HashMap<>();
                hashmap.put("Action","saveUserInfo");
                hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
                hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
                hashmap.put("email",etEmail.getText().toString());
                XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {

                    }
                });
                finish();
                break;
        }

    }
}
