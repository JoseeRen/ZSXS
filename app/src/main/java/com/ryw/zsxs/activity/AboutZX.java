package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.About;
import com.ryw.zsxs.utils.XutilsHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 */

public class AboutZX extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.aboutzx_back)
    ImageView aboutzxBack;
    @BindView(R.id.aboutzx_wv)
    WebView aboutzxWv;



    @Override
    public int getContentViewResId() {
        return R.layout.activity_aboutzx;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        WebSettings ws = aboutzxWv.getSettings();
        ws.setJavaScriptEnabled(true);
        initData();
        initEvent();
    }

    private void initEvent() {
        aboutzxBack.setOnClickListener(this);
    }

    private void initData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Action","AboutUs");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                About about = gson.fromJson(result, About.class);
                Log.e(TAG, "onResponse: "+about.getContent() );
                    aboutzxWv.loadDataWithBaseURL(null,about.getContent(),"text/html","utf-8",null);

            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aboutzx_back:
                finish();
                break;
        }

    }
}
