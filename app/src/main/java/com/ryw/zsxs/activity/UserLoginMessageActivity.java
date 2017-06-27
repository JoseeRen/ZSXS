package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.fragment.User_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 个人信息页面
 */

public class UserLoginMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_userloginmessage_back)
    ImageView ivUserloginmessageBack;
    @BindView(R.id.ll_userpic)
    LinearLayout llUserpic;
    @BindView(R.id.ll_usernickname)
    LinearLayout llUsernickname;
    @BindView(R.id.ll_usersex)
    LinearLayout llUsersex;
    @BindView(R.id.ll_useraddress)
    LinearLayout llUseraddress;
    @BindView(R.id.ll_useremail)
    LinearLayout llUseremail;
    @BindView(R.id.ll_userupdatepsw)
    LinearLayout llUserupdatepsw;
    @BindView(R.id.iv_userpic)
    ImageView ivUserpic;
    @BindView(R.id.tv_usernickname)
    TextView tvUsernickname;
    @BindView(R.id.tv_usernum)
    TextView tvUsernum;
    @BindView(R.id.tv_usersex)
    TextView tvUsersex;
    @BindView(R.id.tv_useraddress)
    TextView tvUseraddress;
    @BindView(R.id.tv_useremail)
    TextView tvUseremail;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userloginmessage;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }


    private void initData() {

    }


    private void initEvent() {
        ivUserloginmessageBack.setOnClickListener(this);
        llUserpic.setOnClickListener(this);
        llUsernickname.setOnClickListener(this);
        llUsersex.setOnClickListener(this);
        llUseraddress.setOnClickListener(this);
        llUseremail.setOnClickListener(this);
        llUserupdatepsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_userloginmessage_back:
                // 退出个人信息页面，返回到中仕个人中心页面
                finish();
                break;
            case R.id.ll_userpic:
                // 弹出对话框 选择 拍照或者从相册中选取
                Toast.makeText(mContext,"弹出对话框选择拍照或者相册选",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_usernickname:
                // 跳转到昵称页面
                Intent userNickname_intent = new Intent(mContext, UserNicknameActivity.class);
                startActivity(userNickname_intent);
                break;
            case R.id.ll_usersex:
                // 弹出对话框选择性别
                Toast.makeText(mContext,"弹出对话框选择男或女",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_useraddress:
                // 跳转到地址信息页面
                Intent userAddress_intent = new Intent(mContext, UserAddressActivity.class);
                startActivity(userAddress_intent);
                break;
            case R.id.ll_useremail:
                // 跳转到邮箱页面
                Intent userEmail_intent = new Intent(mContext, UserEmailActivity.class);
                startActivity(userEmail_intent);
                break;
            case R.id.ll_userupdatepsw:
                // 跳转到修改密码页面
                Intent userUpdatepsw_intent = new Intent(mContext, UserUpdatepswActivity.class);
                startActivity(userUpdatepsw_intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
