package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.HelpXiangQingBean;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/7/1.
 */

public class HelpXiangqing extends BaseActivity {
    @BindView(R.id.helpxiangqing_back)
    ImageView helpxiangqingBack;
    @BindView(R.id.help_xiangqing)
    WebView helpXiangqingWV;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_help_xiangqing;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();

    }


    private void initData() {
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);

        FromNet(id);

    }

    private void FromNet(int id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Action","getHelpContent");
        hashMap.put("id",id+"");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashMap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                HelpXiangQingBean helpXiangQingBean = gson.fromJson(result, HelpXiangQingBean.class);
                helpXiangqingWV.loadDataWithBaseURL(null,helpXiangQingBean.content,"text/html","utf-8",null);
            }
        });
    }

    private void initEvent() {

        helpxiangqingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
