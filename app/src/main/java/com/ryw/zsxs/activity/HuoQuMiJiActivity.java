package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/7/2.
 */

public class HuoQuMiJiActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_huoqumiji_back)
    ImageView ivHuoqumijiBack;
    @BindView(R.id.bt_miji_qiandao)
    Button btMijiQiandao;
    @BindView(R.id.bt_miji_tuijian)
    Button btMijiTuijian;
    @BindView(R.id.bt_miji_chongzhi)
    Button btMijiChongzhi;
    @BindView(R.id.bt_miji_choujiang)
    Button btMijiChoujiang;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_huoqumiji;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        ivHuoqumijiBack.setOnClickListener(this);
        btMijiQiandao.setOnClickListener(this);
        btMijiChongzhi.setOnClickListener(this);
        btMijiChoujiang.setOnClickListener(this);
        btMijiTuijian.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_huoqumiji_back:
                // 返回上级页面
                finish();
                break;
            case R.id.bt_miji_qiandao:
                // 跳转到签到页面
                // TODO: 2017/7/2
                Toast.makeText(HuoQuMiJiActivity.this,"签到页面",Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_miji_choujiang:
                // 跳转到抽奖网站
                // TODO: 2017/7/2
                Toast.makeText(HuoQuMiJiActivity.this,"抽奖网站",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_miji_chongzhi:
                // 跳转到账户中心
                Intent intent = new Intent(HuoQuMiJiActivity.this, UserAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_miji_tuijian:
                // 返回推荐有礼页面
                finish();
                break;
        }
    }
}
