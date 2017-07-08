/*
 * Create on 2017-6-27 下午3:11
 * FileName: Details_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.extextview.ExpandTextView;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CourseHandoutBean;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.MyListview;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面详情
 */

public class Details_Fragment extends BaseFragment {
    static Details_Fragment instance;

    @BindView(R.id.expand_text_view_kcinfo)
    ExpandTextView expandTextViewKcinfo;

    @BindView(R.id.lv_fragment_details_jiangyi)
    MyListview lvFragmentDetailsJiangyi;
    Unbinder unbinder;
    private static String kcInfo;
    private static String kcId;

    public static Details_Fragment getInstance(String kc_info, String kc_id) {
        kcInfo = kc_info;
        kcId = kc_id;
        if (instance == null) {
            instance = new Details_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        expandTextViewKcinfo.setText(kcInfo);
        //获取课程讲义  讲义下载地址，为空表示用户没有权限下载
        getCourseHandout();
    }

    public void getCourseHandout() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "getCourseHandout");
        map.put("Kc_id", kcId);
        map.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                CourseHandoutBean handoutBean = gson.fromJson(result, CourseHandoutBean.class);
                lvFragmentDetailsJiangyi.setAdapter(new MyAdapter(handoutBean.getList()));
               postComplated();
            }
        });

    }

    private void postComplated() {
        Log.e(TAG, "postComplated: " + "第二个fragment加载完成事件");
        Bundle bundle = new Bundle();
        bundle.putInt("flag", 1);
        EventBus.getDefault().post(new DataLoadComplatedEvent(bundle));
    }

    class MyAdapter extends BaseAdapter {

        private final List<CourseHandoutBean.ListBean> list;
        @BindView(R.id.tv_item_lv_handout_title)
        TextView tvItemLvHandoutTitle;
        @BindView(R.id.ib_item_lv_handout_download)
        ImageButton ibItemLvHandoutDownload;

        public MyAdapter(List<CourseHandoutBean.ListBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
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
            ViewHolder holer = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_lv_details_fragment, null);
                holer = new ViewHolder(view);
                view.setTag(holer);
            } else {
                holer = (ViewHolder) view.getTag();


            }

            holer.tvItemLvHandoutTitle.setText(list.get(i).getTitle());
            holer.ibItemLvHandoutDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "下载讲义", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }


        class ViewHolder {
            @BindView(R.id.tv_item_lv_handout_title)
            TextView tvItemLvHandoutTitle;
            @BindView(R.id.ib_item_lv_handout_download)
            ImageButton ibItemLvHandoutDownload;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_details;
    }


}
