package com.ryw.zsxs.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/24.
 * 个人信息中昵称页面
 */

public class UserNicknameActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_usernickname_back)
    ImageView ivUsernicknameBack;
    @BindView(R.id.bt_nickname_save)
    Button btNicknameSave;
    @BindView(R.id.et_nickname)
    EditText etNickname;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_usernickname;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initData() {
        // 获取传过来的数据（昵称）
        Intent intent = getIntent();
        String userNickname = intent.getStringExtra("userNickname");
        etNickname.setText(userNickname.toString());
    }


    private void initEvent() {
        ivUsernicknameBack.setOnClickListener(this);
        btNicknameSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_usernickname_back:
                finish();
                break;
            case R.id.bt_nickname_save:
                // 把修改的昵称提交到服务器上；
                HashMap<String, String> hashmap = new HashMap<>();
                hashmap.put("Action","saveUserInfo");
                hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
                hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
                hashmap.put("nickname",etNickname.getText().toString());
                XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {

                    }
                });
                finish();
        }
    }
}
