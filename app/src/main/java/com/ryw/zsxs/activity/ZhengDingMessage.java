package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/28.
 */

public class ZhengDingMessage extends BaseActivity implements View.OnClickListener {


    //返回按钮
    @BindView(R.id.zhengdingmessage_back)
    ImageView zhengdingmessageBack;
    //个人征订的title
    @BindView(R.id.zhengdingmessage_gr_title)
    TextView zhengdingmessageGrTitle;
    //个人征订的content
    @BindView(R.id.zhengdingmessage_gr_content)
    TextView zhengdingmessageGrContent;
    //个人征订的time
    @BindView(R.id.zhengdingmessage_gr_time)
    TextView zhengdingmessageGrTime;
    //个人征订布局
    @BindView(R.id.zhengdingmessage_gr)
    LinearLayout zhengdingmessageGr;

    //全部征订的title
    @BindView(R.id.zhengdingmessage_sy_title)
    TextView zhengdingmessageSyTitle;
    //全部征订的content
    @BindView(R.id.zhengdingmessage_sy_content)
    TextView zhengdingmessageSyContent;
    //全部征订的zhuijia
    @BindView(R.id.zhengdingmessage_sy_zhuijia)
    TextView zhengdingmessageSyZhuijia;
    //全部征订的time
    @BindView(R.id.zhengdingmessage_sy_time)
    TextView zhengdingmessageSyTime;
    //全部征订的布局
    @BindView(R.id.zhengdingmessage_sy)
    LinearLayout zhengdingmessageSy;
    private String GRtitle;
    private String SYtitle;
    private String SYcontent;
    private String SYtime;
    private String GRtime;
    private String SYhot;
    private String GRhf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentViewResId() {

        return R.layout.activity_zhengdingmessage;

    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();

    }

    private void initEvent() {

        zhengdingmessageBack.setOnClickListener(this);
        zhengdingmessageGrTitle.setText(GRtitle);
        zhengdingmessageGrTime.setText(GRtime);
        /*if (GRhf==null){
            zhengdingmessageGrContent.setText("暂无回复");
        }else {
            zhengdingmessageGrContent.setText(GRhf);
        }*/


        zhengdingmessageSyTitle.setText(SYtitle);
        zhengdingmessageSyContent.setText(SYcontent);
        zhengdingmessageSyTime.setText(SYtime);
        if (SYhot==null){
            zhengdingmessageSyZhuijia.setText("已有1位社追加征订");
        }else {
            zhengdingmessageSyZhuijia.setText("已有"+SYhot+"位社追加征订");
        }




    }

    private void initData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        GRtitle = intent.getStringExtra("GRtitle");
        GRtime = intent.getStringExtra("GRtime");
        //GRhf = intent.getStringExtra("GRhf");

        if (type.equals("GR")){
            zhengdingmessageGr.setVisibility(View.VISIBLE);
            zhengdingmessageSy.setVisibility(View.GONE);


        }else {

            zhengdingmessageSy.setVisibility(View.VISIBLE);
            zhengdingmessageGr.setVisibility(View.GONE);
            SYtitle = intent.getStringExtra("SYtitle");
            SYcontent = intent.getStringExtra("SYcontent");
            SYtime = intent.getStringExtra("SYtime");
            SYhot = intent.getStringExtra("SYhot");

        }



    }




    /*点击事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhengdingmessage_back:
                finish();
                break;


        }
    }
}
