package com.ryw.zsxs.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 账户中心
 */

public class UserAccountActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_useraccount_back)
    ImageView ivUseraccountBack;
    @BindView(R.id.iv_useraccount_tx)
    ImageView ivUseraccountTx;
    @BindView(R.id.tv_useraccount_name)
    TextView tvUseraccountName;
    @BindView(R.id.ll_useraccount_txandname)
    LinearLayout llUseraccountTxandname;
    @BindView(R.id.tv_useraccount_jf)
    TextView tvUseraccountJf;
    @BindView(R.id.ll_useraccount_exchange)
    LinearLayout llUseraccountExchange;
    @BindView(R.id.tv_useraccount_xb)
    TextView tvUseraccountXb;
    @BindView(R.id.tv_useraccount_jfsz)
    TextView tvUseraccountJfsz;
    @BindView(R.id.tv_useraccount_xbsz)
    TextView tvUseraccountXbsz;
    @BindView(R.id.tv_useraccount_ljxs)
    TextView tvUseraccountLjxs;
    @BindView(R.id.iv_useraccount_khjt)
    ImageView ivUseraccountKhjt;
    @BindView(R.id.ll_useraccount_czkh)
    LinearLayout llUseraccountCzkh;
    @BindView(R.id.et_useraccount_kh)
    EditText etUseraccountKh;
    @BindView(R.id.bt_useraccount_khcz)
    Button btUseraccountKhcz;
    @BindView(R.id.ll_useraccount_kh)
    LinearLayout llUseraccountKh;
    @BindView(R.id.iv_useraccount_zfbjt)
    ImageView ivUseraccountZfbjt;
    @BindView(R.id.ll_useraccount_zfb)
    LinearLayout llUseraccountZfb;
    @BindView(R.id.et_useraccount_zfjb)
    EditText etUseraccountZfjb;
    @BindView(R.id.bt_useraccount_zfbcz)
    Button btUseraccountZfbcz;
    @BindView(R.id.ll_useraccount_zfbcz)
    LinearLayout llUseraccountZfbcz;
    @BindView(R.id.iv_useraccount_wxjt)
    ImageView ivUseraccountWxjt;
    @BindView(R.id.ll_useraccount_wx)
    LinearLayout llUseraccountWx;
    @BindView(R.id.et_useraccount_wxjb)
    EditText etUseraccountWxjb;
    @BindView(R.id.bt_useraccount_wxcz)
    Button btUseraccountWxcz;
    @BindView(R.id.ll_useraccount_wxcz)
    LinearLayout llUseraccountWxcz;

    private static boolean IS_TOP = false;
    public UserInfoBean userInfoBean;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_useraccount;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initListener();
    }

    @Override
    protected void onResume() {

        super.onResume();
        // 先获取一下头像、昵称、积分和学币的数据
        final ImageOptions options = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setIgnoreGif(true)
                .setCircular(true)
                .build();
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getUserInfo");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                userInfoBean = gson.fromJson(result, UserInfoBean.class);
                x.image().loadDrawable(userInfoBean.Pic, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        ivUseraccountTx.setImageDrawable(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                tvUseraccountName.setText(userInfoBean.Nicename);
                tvUseraccountJf.setText(userInfoBean.Jifen+"");
                tvUseraccountXb.setText(userInfoBean.Money+"");

            }
        });
    }

    private void initData() {
        // 初始化数据的时候 让卡号充值，支付宝充值，微信充值的条目不可见并且图片是向下的
        llUseraccountKh.setVisibility(View.GONE);
        llUseraccountZfbcz.setVisibility(View.GONE);
        llUseraccountWxcz.setVisibility(View.GONE);

        ivUseraccountKhjt.setImageResource(R.mipmap.buttom_smart);
        ivUseraccountZfbjt.setImageResource(R.mipmap.buttom_smart);
        ivUseraccountWxjt.setImageResource(R.mipmap.buttom_smart);
    }

    private void initListener() {
        ivUseraccountBack.setOnClickListener(this);
        llUseraccountTxandname.setOnClickListener(this);
        llUseraccountExchange.setOnClickListener(this);
        tvUseraccountJfsz.setOnClickListener(this);
        tvUseraccountXbsz.setOnClickListener(this);
        tvUseraccountLjxs.setOnClickListener(this);

        // 条目的点击事件
        llUseraccountCzkh.setOnClickListener(this);
        llUseraccountZfb.setOnClickListener(this);
        llUseraccountWx.setOnClickListener(this);

        // 充值按钮的点击事件
        btUseraccountKhcz.setOnClickListener(this);
        btUseraccountZfbcz.setOnClickListener(this);
        btUseraccountWxcz.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_useraccount_back:
                // 返回上级页面
                finish();
                break;
            case R.id.ll_useraccount_txandname:
                // 点击头像或者昵称跳转到个人信息页面
                Intent txandname_intent = new Intent(mContext, UserLoginMessageActivity.class);
                startActivity(txandname_intent);
                break;
            case R.id.ll_useraccount_exchange:
                // 跳转到兑换页面
                // TODO: 2017/7/4
                Intent exchange_intent = new Intent(mContext, ExchangeActivity.class);
                exchange_intent.putExtra("JiFen",userInfoBean.Jifen);
                exchange_intent.putExtra("XueBi",userInfoBean.Money);
                startActivity(exchange_intent);
                break;
            case R.id.tv_useraccount_jfsz:
                // 跳转到积分收支页面
                Intent userJifen_intent = new Intent(mContext, UserJifenActivity.class);
                startActivity(userJifen_intent);
                break;
            case R.id.tv_useraccount_xbsz:
                // 跳转到学币收支记录页面
                Intent userXuebi_intent = new Intent(mContext, UserXuebiActivity.class);
                startActivity(userXuebi_intent);
                break;
            case R.id.tv_useraccount_ljxs:
                // 跳转到学识记录页面
                Intent userXueshi_intent = new Intent(mContext, UserXueshiActivity.class);
                startActivity(userXueshi_intent);
                break;
            case R.id.ll_useraccount_czkh:
                // 点击显示 充值卡号条目并且向下的图片变成向上的图片
                if (IS_TOP){
                    llUseraccountKh.setVisibility(View.GONE);
                    ivUseraccountKhjt.setImageResource(R.mipmap.buttom_smart);
                    IS_TOP=false;
                }else {
                    llUseraccountKh.setVisibility(View.VISIBLE);
                    ivUseraccountKhjt.setImageResource(R.mipmap.buttom_smart_top);
                    IS_TOP=true;
                }
                break;
            case R.id.ll_useraccount_zfb:
                // 点击显示 支付宝充值条目并且向下的图片变成向上的图片
                if (IS_TOP){
                    llUseraccountZfbcz.setVisibility(View.GONE);
                    ivUseraccountZfbjt.setImageResource(R.mipmap.buttom_smart);
                    IS_TOP=false;
                }else {
                    llUseraccountZfbcz.setVisibility(View.VISIBLE);
                    ivUseraccountZfbjt.setImageResource(R.mipmap.buttom_smart_top);
                    IS_TOP=true;
                }
                break;
            case R.id.ll_useraccount_wx:
                // 点击显示 微信充值条目并且向下的图片变成向上的图片
                if (IS_TOP){
                    llUseraccountWxcz.setVisibility(View.GONE);
                    ivUseraccountWxjt.setImageResource(R.mipmap.buttom_smart);
                    IS_TOP=false;
                }else {
                    llUseraccountWxcz.setVisibility(View.VISIBLE);
                    ivUseraccountWxjt.setImageResource(R.mipmap.buttom_smart_top);
                    IS_TOP=true;
                }
                break;
            case R.id.bt_useraccount_khcz:
                // 输入卡号充值
                Toast.makeText(mContext,"输入卡号充值！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_useraccount_zfbcz:
                // 支付宝充值
                Toast.makeText(mContext,"支付宝充值！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_useraccount_wxcz:
                // 微信充值
                Toast.makeText(mContext,"微信充值！",Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
