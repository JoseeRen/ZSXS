package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/24.
 * 个人信息中修改密码页面
 */

public class UserUpdatepswActivity extends BaseActivity {
    @BindView(R.id.iv_userupdatepsw_back)
    ImageView ivUserupdatepswBack;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.et_newpsw)
    EditText etNewpsw;
    @BindView(R.id.et_renewpsw)
    EditText etRenewpsw;
    @BindView(R.id.bt_useryespsw)
    Button btUseryespsw;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userupdatepsw;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();

    }

    private void initData() {
        // 判断3个editview是否为空
        String yuan_psw = etPsw.getText().toString().trim();
        String xin_psw = etNewpsw.getText().toString().trim();
        String re_newpsw = etRenewpsw.getText().toString().trim();
        if (TextUtils.isEmpty(yuan_psw)){
            Toast.makeText(this, "原密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(xin_psw)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(re_newpsw)){
            Toast.makeText(this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (yuan_psw.length()<6 || xin_psw.length()<6 || re_newpsw.length()<6){
            Toast.makeText(this, "密码长度不能小于6个字符", Toast.LENGTH_SHORT).show();
        }

    }

    private void initEvent() {

    }

}
