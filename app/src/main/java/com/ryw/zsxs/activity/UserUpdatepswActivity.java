package com.ryw.zsxs.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.MD5utils;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;


/**
 * Created by Y.Q on 2017/6/24.
 * 个人信息中修改密码页面
 */

public class UserUpdatepswActivity extends BaseActivity implements View.OnClickListener {
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
    private String yuan_psw;
    private String xin_psw;
    private String re_newpsw;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userupdatepsw;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initEvent();
    }

    private void initEvent() {
        ivUserupdatepswBack.setOnClickListener(this);
        btUseryespsw.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_userupdatepsw_back:
                // 返回个人信息界面
                finish();
                break;
            case R.id.bt_useryespsw:
                // 判断editview里面内容是否一致，并且把修改的x新密码提交到服务器上
                puanduanpsw();
                break;
        }
    }

    private void puanduanpsw() {
        // 判断3个editview是否为空
        yuan_psw = etPsw.getText().toString().trim();
        xin_psw = etNewpsw.getText().toString().trim();
        re_newpsw = etRenewpsw.getText().toString().trim();

        if (TextUtils.isEmpty(yuan_psw)){
            Toast.makeText(this, "原密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(xin_psw)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(re_newpsw)){
            Toast.makeText(this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (yuan_psw.length()<6 || xin_psw.length()<6 || re_newpsw.length()<6){
            Toast.makeText(this, "密码长度不能小于6个字符", Toast.LENGTH_SHORT).show();
        }

        // 获取旧密码并且判断
        String jiu_psw = SpUtils.getString(UserUpdatepswActivity.this, LoginAcitvity.PWD);
        Log.e(TAG, "旧密码: ----"+jiu_psw);
        if (jiu_psw.equals(MD5utils.encode(yuan_psw))&&xin_psw.equals(re_newpsw)){
            postXinPsw();
        }

        if (!(xin_psw.equals(re_newpsw))){
            Toast.makeText(this, "两次输入的密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
        }

        // 如果两次输入的密码不一样，弹出对话框
        if (!(jiu_psw.equals(MD5utils.encode(yuan_psw)))){
            // 弹出一个对话框
            final AlertDialog.Builder tishiDialog =
                    new AlertDialog.Builder(UserUpdatepswActivity.this);

            TextView tv = new TextView(UserUpdatepswActivity.this);
            tv.setText("提示：");    //内容
            tv.setTextSize(15);//字体大小
            tv.setPadding(30, 20, 10, 10);//位置
            tv.setTextColor(getResources().getColor(R.color.tishi_text));//颜色
            tishiDialog.setCustomTitle(tv);
            tishiDialog.setMessage("原始密码不正确");
            tishiDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });

            tishiDialog.create().show();
        }
    }


    /**
     * 提交新密码的方法
     */
    private void postXinPsw() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action","ModPwd");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
        hashmap.put("Oldpwd", MD5utils.encode(yuan_psw));
        hashmap.put("Newpwd",MD5utils.encode(xin_psw));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse:-----------------------------"+result+"" );
            }
        });
        Intent intent = new Intent(UserUpdatepswActivity.this, LoginAcitvity.class);
        startActivity(intent);
    }


}
