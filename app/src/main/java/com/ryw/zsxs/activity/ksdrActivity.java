package com.ryw.zsxs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.bean.GetZTBean;
import com.ryw.zsxs.utils.XutilsHttp;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shishaoyou on 2017/6/25.
 * 2017下的图片activiy
 */

public class ksdrActivity extends Activity {
    @BindView(R.id.ksdr_list)
    ListView list;
    private List<GetZTBean.ListBean> getztlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ksdritem);
        ButterKnife.bind(this);

        initdata();
        initevent();
    }

    private void initevent() {
        list.setAdapter(new MyAdatper());
    }

    private void initdata() {
        Bundle bundle = getIntent().getExtras();
        GetZTBean getztBean = (GetZTBean) bundle.getSerializable("getztBean");
        getztlist = getztBean.getList();
        list.setAdapter(new MyAdatper());

    }


    class MyAdatper extends BaseAdapter {

        @Override
        public int getCount() {
            return getztlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(ksdrActivity.this, R.layout.activity_ksdrlist_item, null);
            ImageView iv = view.findViewById(R.id.iv1);
            String imgURL = getztlist.get(i).getImgURL();
            XutilsHttp.getInstance().bindCommonImage(iv, imgURL, false);
            return view;
        }
    }
}
