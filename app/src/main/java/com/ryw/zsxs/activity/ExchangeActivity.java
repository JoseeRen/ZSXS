package com.ryw.zsxs.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/7/4.
 */

public class ExchangeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_exchange_back)
    ImageView ivExchangeBack;
    @BindView(R.id.tv_exchange_xuebi)
    TextView tvExchangeXuebi;
    @BindView(R.id.tv_exchange_xb)
    TextView tvExchangeXb;
    @BindView(R.id.iv_exchange)
    ImageView ivExchange;
    @BindView(R.id.tv_exchange_jifen)
    TextView tvExchangeJifen;
    @BindView(R.id.tv_exchange_jf)
    TextView tvExchangeJf;
    @BindView(R.id.et_exchange)
    EditText etExchange;
    @BindView(R.id.tv_exchange_xuebi1)
    TextView tvExchangeXuebi1;
    @BindView(R.id.tv_exchange_result)
    TextView tvExchangeResult;
    @BindView(R.id.tv_exchange_jifen1)
    TextView tvExchangeJifen1;
    @BindView(R.id.bt_exchange_yes)
    Button btExchangeYes;


    private static boolean IS_CONTENT = true; // true为学币  false为积分
    private String jiFen;
    private String xueBi;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_exchange;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initListener();
    }

    private void initData() {
        // 先获取学币和积分
        Intent intent = getIntent();
        jiFen = intent.getStringExtra("JiFen");
        xueBi = intent.getStringExtra("XueBi");
        tvExchangeJf.setText(jiFen);
        tvExchangeXb.setText(xueBi);

    }

    private void initListener() {
        ivExchangeBack.setOnClickListener(this);
        ivExchange.setOnClickListener(this);
        btExchangeYes.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exchange_back:
                // 返回上级页面
                finish();
                break;
            case R.id.iv_exchange:
                // 点击转换图片，实现积分与学币直接的转换（只是文本内容之间的转换）
                if (IS_CONTENT){
                    // 是学币的情况下，点击转换为积分
                    tvExchangeXuebi.setText("学币");
                    tvExchangeXb.setText(xueBi);
                    tvExchangeXuebi1.setText("学币");
                    tvExchangeJifen.setText("积分");
                    tvExchangeJf.setText(jiFen);
                    tvExchangeJifen1.setText("积分");

                    IS_CONTENT=false;
                }else {
                    // 是积分的情况下，点击转换为学币
                    tvExchangeXuebi.setText("积分");
                    tvExchangeXb.setText(jiFen);
                    tvExchangeXuebi1.setText("积分");
                    tvExchangeJifen.setText("学币");
                    tvExchangeJf.setText(xueBi);
                    tvExchangeJifen1.setText("学币");
                    IS_CONTENT = true;
                }
                break;
            case  R.id.bt_exchange_yes:
                // 确认转换
                getexchange();
                break;

        }
    }

    private void getexchange() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "exchangeMoney");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: ------exchange------"+result );
            }
        });
    }

}
