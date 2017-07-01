package com.ryw.zsxs.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/27.
 */

public class WoYaoZhengDing extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.woyaozhengding_back)
    ImageView woyaozhengding_back;

    @BindView(R.id.woyaozhengding_zdTitle_content)
    EditText woyaozhengdingTitle;

    @BindView(R.id.woyaozhengding_zdContent_content)
    EditText woyaozhengdingContent;

    @BindView(R.id.woyaozhengding_tijiao)
    Button woyaozhengdingTijiao;

    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_woyaozhengding;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }


    private void initData() {


    }


    private void initEvent() {
        woyaozhengdingTitle.setOnClickListener(this);
        woyaozhengdingContent.setOnClickListener(this);
        woyaozhengdingTijiao.setOnClickListener(this);
        woyaozhengding_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.woyaozhengding_back:
                finish();
                break;
            case R.id.woyaozhengding_tijiao:
                TiJiao();
                break;
        }
    }

    private void TiJiao() {

        title = woyaozhengdingTitle.getText().toString().trim();
        content = woyaozhengdingContent.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {

            Toast.makeText(mContext, "征订标题不能为空", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(content)) {

            Toast.makeText(mContext, "征订内容不能为空", Toast.LENGTH_SHORT).show();

        } else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("Action", "SaveSubscription");
            hashMap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
            hashMap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
            hashMap.put("title", title);
            hashMap.put("content", content);


            XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
                @Override
                public void onResponse(String result) {
                    Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.woyaozhengding_duhuakuang);

                    Window dialogWindow = dialog.getWindow();



                    WindowManager m = getWindowManager();
                    Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                    WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                    p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.6
                    p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
                    dialogWindow.setAttributes(p);
                    dialog.show();
                }

            });

        }
    }

}
