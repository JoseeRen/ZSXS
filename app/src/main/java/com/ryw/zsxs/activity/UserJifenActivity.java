package com.ryw.zsxs.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Y.Q on 2017/6/23.
 * 中仕个人中心 积分收支记录页面
 */

public class UserJifenActivity extends BaseActivity  {
    @BindView(R.id.iv_userjifen_back)
    ImageView ivUserjifenBack;
    @BindView(R.id.ptf_listview)
    PullToRefreshListView ptfListview;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_userjifen;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initListener();
    }



    private void initData() {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action","getJifenRecords");
        hashmap.put("acode", SpUtils.getString(mContext,LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: "+result );
                //Gson gson = new Gson();
               // gson.fromJson();
            }
        });

    }


    private void initListener() {

        ivUserjifenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
