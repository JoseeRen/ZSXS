package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.GetZTBean;
import com.ryw.zsxs.bean.GetZTShow;
import com.ryw.zsxs.utils.XutilsHttp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.utils.Log;

/**
 * Created by shishaoyou on 2017/6/25.
 * 2017下的图片activiy
 */

public class ksdrActivity extends BaseActivity {

    @BindView(R.id.home_2017_back)
    TextView home2017Back;
    @BindView(R.id.ksdr_list)
    PullToRefreshListView list;

    private List<GetZTBean.ListBean> getztlist;
    private GetZTBean getztBean;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_ksdritem;

    }

    @Override
    public void init(Bundle savedInstanceState) {
        initdata();
        initevent();
    }

    private void initevent() {
        home2017Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回上一页

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //list的条目点击事件
                getDatainternet(i);
            }
        });
    }

    private void getDatainternet(int i) {
        String url = "http://api.chinaplat.com/getval_2017?Action=getZTShow&Types=1&ztid=" + getztlist.get(i).getZtid();
        Log.e("lllllllll" + url);
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据2017--的第二层
                parsedatell(result);
            }
        });
    }

    private void parsedatell(String result) {
        Gson gson = new Gson();
        GetZTShow getZTShowBean = gson.fromJson(result, GetZTShow.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("getZTShowBean", getZTShowBean);
        startActivity(T20172Activity.class, bundle);


    }


    private void initdata() {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        home2017Back.setText(title);
        getztBean = (GetZTBean) bundle.getSerializable("getztBean");
        getztlist = getztBean.getList();
        initlistview();
        list.setAdapter(new MyAdatper());

    }

    private void initlistview() {
        //初始化列表
        inittext();
        list.setMode(PullToRefreshBase.Mode.BOTH);
        //2017第一层的条目点击事件
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void inittext() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = list.getLoadingLayoutProxy(true, false);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String str = format.format(new Date());
        list.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载中...");// 刷新时
        startLabels.setReleaseLabel("松手可刷新...");// 下来达到一定距离时，显示的提示
//     设置上拉刷新文本
        ILoadingLayout endLabels = list.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载中...");// 刷新时
        endLabels.setReleaseLabel("松手可刷新");// 下来达到一定距离时，显示的提示
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
