/*
 * Create on 2017-6-8 下午5:49
 * FileName: SplashActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.ADBean;
import com.ryw.zsxs.utils.SharedPreferences;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tiansj on 15/7/29.
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_ad)
    ImageView ivAd;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initADPic();
        final boolean first_boot = SharedPreferences.getInstance().getBoolean("first_boot", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (first_boot) {
                    //第一次进入  到引导页面
                    startActivity(GuideActivity.class, null);

                } else {
                    startActivity(MainActivity.class, null);
                }
                finish();
            }
        }, 2000);

    }

    /**
     * 初始化splash页面的ad图片
     */
    private void initADPic() {
        final String adPath = Constant.SAVE_AD_PATH;
        File file = new File(adPath);
if (file.exists()){

    x.image().bind(ivAd,adPath);
}else {
    XutilsHttp.getInstance().get(Constant.HOSTNAME + Constant.ACTION_GETADPIC, null, new XutilsHttp.XCallBack() {
        @Override
        public void onResponse(String result) {
            Gson gson = new Gson();
            ADBean adBean = gson.fromJson(result, ADBean.class);

            XutilsHttp.getInstance().downLoadFile(adBean.getPic(), adPath,null, new XutilsHttp.XDownLoadCallBack() {
                @Override
                public void onResponse(File result) {
                    Log.e(">>>>",result.getName());
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onResponse(String result) {

                }


            });
        }


    });
}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
