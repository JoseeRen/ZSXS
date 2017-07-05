package com.ryw.zsxs.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.TuiJianBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 推荐有礼
 */

public class UserShareActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_usershare_back)
    ImageView ivUsershareBack;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.tv_tuijianma)
    TextView tvTuijianma;
    @BindView(R.id.bt_tjmfuzhi)
    Button btTjmfuzhi;
    @BindView(R.id.bt_tjljfuzhi)
    Button btTjljfuzhi;
    @BindView(R.id.bt_tjljshare)
    Button btTjljshare;
    @BindView(R.id.tv_tuijianrenshu)
    TextView tvTuijianrenshu;
    @BindView(R.id.tv_tuijianjifen)
    TextView tvTuijianjifen;
    @BindView(R.id.iv_to_jifen_icon)
    ImageView ivToJifenIcon;
    private TuiJianBean tuiJianBean;
    private String tjljurl;
    private ClipboardManager cm;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_usershare;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initEvent();
    }

    private void initEvent() {
        ivUsershareBack.setOnClickListener(this);
        ivErweima.setOnClickListener(this);
        btTjmfuzhi.setOnClickListener(this);
        btTjljfuzhi.setOnClickListener(this);
        btTjljshare.setOnClickListener(this);
        ivToJifenIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_usershare_back:
                // 返回
                finish();
                break;
            case R.id.iv_erweima:
                // 跳转到二维码页面
                Intent myerweima_intent = new Intent(UserShareActivity.this, MyErweimaActivity.class);
                startActivity(myerweima_intent);
                break;
            case R.id.bt_tjmfuzhi:
                // 复制推荐码

                break;
            case R.id.bt_tjljfuzhi:
                // 复制推荐链接
                copytuijianlianjie();
                Toast.makeText(UserShareActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_tjljshare:
                // 把推荐链接分享到
                copytuijianlianjie();
                showshareDialog();
                break;
            case R.id.iv_to_jifen_icon:
                // 跳转到积分获取秘籍页面
                Intent intent = new Intent(UserShareActivity.this,HuoQuMiJiActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 复制推荐链接
     */
    private void copytuijianlianjie() {
        String url = tuiJianBean.getUrl();
        String acode = SpUtils.getString(mContext, LoginAcitvity.ACODE);
        String uid = SpUtils.getString(mContext, LoginAcitvity.USERNAME);
        tjljurl = url+"acode="+acode+"Uid="+uid;

        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(tjljurl);
    }

    // 分享到对话框

    private void showshareDialog() {
        AlertDialog.Builder sharedialog = new AlertDialog.Builder(UserShareActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_showdialog, null);//获取自定义布局
        sharedialog.setView(layout);
        sharedialog.setTitle("分享到");

        // 微信好友
        TextView wecha = layout.findViewById(R.id.wecha);
        wecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserShareActivity.this,"微信好友",Toast.LENGTH_SHORT).show();
                // TODO: 2017/7/2
            }
        });

        // 朋友圈
        TextView wxcircle = layout.findViewById(R.id.wxcircle);
        wxcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserShareActivity.this,"朋友圈",Toast.LENGTH_SHORT).show();
                // TODO: 2017/7/2
            }
        });

        // QQ空间
        TextView qzone = layout.findViewById(R.id.qzone);
        qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserShareActivity.this,"QQ空间",Toast.LENGTH_SHORT).show();
                // TODO: 2017/7/2
            }
        });

        // 新浪微博
        TextView sina = layout.findViewById(R.id.sina);
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserShareActivity.this,"新浪微博",Toast.LENGTH_SHORT).show();
                // TODO: 2017/7/2
            }
        });

        // QQ好友
        TextView qq = layout.findViewById(R.id.qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserShareActivity.this,"QQ好友",Toast.LENGTH_SHORT).show();
                // TODO: 2017/7/2
            }
        });

        // 取消按钮
        sharedialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
               arg0.dismiss();
            }
        });

        sharedialog.create().show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 首先应该获取数据
        getTuijianData();
    }


    /**
     * 从服务器上获取推荐有礼的数据
     */
    private void getTuijianData() {
        // 获取推荐码设置给tvTuijianma
        // TODO: 2017/7/2


        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getTjInfo");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "Tuijian-------onResponse: " + result);
                Gson gson = new Gson();
                tuiJianBean = gson.fromJson(result, TuiJianBean.class);
                // 把获取到的积分设置给相应控件
                int jifen = tuiJianBean.getJifen();
                tvTuijianjifen.setText(jifen+"积分");

                // 把获取到的人数设置给相应控件
                int nums = tuiJianBean.getNums();
                tvTuijianrenshu.setText(nums+"人");
            }
        });
    }


}
