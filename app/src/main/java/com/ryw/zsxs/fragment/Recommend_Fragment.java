/*
 * Create on 2017-6-27 下午3:12
 * FileName: Recommend_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.VideoPlayActivity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.SameCourse;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.utils.XutilsHttp;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面的推荐
 */

public class Recommend_Fragment extends BaseFragment {
    static Recommend_Fragment instance;
    @BindView(R.id.lv_recommend_fragment)
    ListView lvRecommendFragment;
    static String kcTypes;
    static String kcId;
    private SameCourse sameCourse;

    public static Recommend_Fragment getInstance(String kctypes, String kcid) {
        kcTypes = kctypes;
        kcId = kcid;
        if (instance == null) {
            instance = new Recommend_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        lvRecommendFragment.setOnItemClickListener(new MyOnItemClickListener());
        getDataFormNet();
    }


    public void getDataFormNet() {
        HashMap<String, String> map = new HashMap<>();

        map.put("Action", "GetSameCourse");
        map.put("cid", kcId);
        map.put("kc_types", kcTypes);
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                sameCourse = gson.fromJson(result, SameCourse.class);
                MyAdapter myAdapter = new MyAdapter(sameCourse);
                lvRecommendFragment.setAdapter(myAdapter);
                postComplated();
            }

        });
    }


    class MyAdapter extends BaseAdapter {

        private final SameCourse sameCourse;

        public MyAdapter(SameCourse sameCourse) {
            this.sameCourse = sameCourse;
        }

        @Override
        public int getCount() {
            return sameCourse.getSameCourse().size();
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_xuankedetail_pvlistview, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            XutilsHttp.getInstance().bindCommonImage(viewHolder.ivItemXuankedetailPv, sameCourse.getSameCourse().get(i).getImg(), true);
            viewHolder.tvTitleItemXuankedetailPv.setText(sameCourse.getSameCourse().get(i).getTitle());
            viewHolder.tvInfoItemXuankedetailPv.setText(sameCourse.getSameCourse().get(i).getInfo());
            Log.e(TAG, "getView: " + sameCourse.getSameCourse().get(i).getInfo());
            viewHolder.tvKeshiItemXuankedetailPv.setText(sameCourse.getSameCourse().get(i).getKeshi() + "课时");
            viewHolder.tvJifenItemXuankedetailPv.setText(sameCourse.getSameCourse().get(i).getMoney() + "积分");
            viewHolder.tvDianjiliangItemXuankedetailPv.setText(sameCourse.getSameCourse().get(i).getHot() + "");


            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_item_xuankedetail_pv)
            ImageView ivItemXuankedetailPv;
            @BindView(R.id.tv_title_item_xuankedetail_pv)
            TextView tvTitleItemXuankedetailPv;
            @BindView(R.id.tv_keshi_item_xuankedetail_pv)
            TextView tvKeshiItemXuankedetailPv;
            @BindView(R.id.tv_jifen_item_xuankedetail_pv)
            TextView tvJifenItemXuankedetailPv;
            @BindView(R.id.tv_dianjiliang_item_xuankedetail_pv)
            TextView tvDianjiliangItemXuankedetailPv;
            @BindView(R.id.tv_info_item_xuankedetail_pv)
            TextView tvInfoItemXuankedetailPv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


    }

    private void postComplated() {
        Log.e(TAG, "postComplated: " + "加载完成事件");
        Bundle bundle = new Bundle();
        bundle.putInt("flag", 2);
        EventBus.getDefault().post(new DataLoadComplatedEvent(bundle));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }


    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //准备跳转页面   需要kc_id  Kc_types
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", sameCourse.getSameCourse().get(0));

            // tvXuankedetailTitle.setText("视频中心");
            startActivity(VideoPlayActivity.class, bundle);
            getActivity().finish();
        }
    }
}
