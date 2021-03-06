/*
 * Create on 2017-6-27 下午3:10
 * FileName: Catalog_Fragment.java
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CourseDetailBean;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Mr_Shadow on 2017/6/27.
 * 视频播放页面的  目录
 * http://api.chinaplat.com/getval_2017?kc_types=0&Action=GetCourseInfo&kc_id=140703
 */

public class Catalog_Fragment extends BaseFragment {
    static Catalog_Fragment instance;
    private static String kc_id;
    @BindView(R.id.tv_catalogfragment_kctitle)
    TextView tvCatalogfragmentKctitle;
    @BindView(R.id.tv_catalogfragment_kcjishu)
    TextView tvCatalogfragmentKcjishu;
    @BindView(R.id.btn_catalogfragment_xuanji)
    Button btnCatalogfragmentXuanji;
    @BindView(R.id.btn_catalogfragment_cachedownload)
    Button btnCatalogfragmentCachedownload;
    @BindView(R.id.lv_catalogfragment_coursecatalo)
    ListView lvCatalogfragmentCoursecatalo;

    @BindView(R.id.ll_fragment_catalog_1)
    LinearLayout llFragmentCatalog1;
    Unbinder unbinder;
    private CourseDetailBean courseDetail;
    private Bundle bundle;
    private int currentPosition=0;

    public static Catalog_Fragment getInstance(String kc_ids) {
        kc_id = kc_ids;
        if (instance == null) {
            instance = new Catalog_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initData();
    }


    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("kc_types", "0");
        map.put("Action", "GetCourseInfo");
        map.put("kc_id", kc_id);
        map.put("Acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                courseDetail = gson.fromJson(result, CourseDetailBean.class);
                lvCatalogfragmentCoursecatalo.setAdapter(new MyAdapter(courseDetail.getFiles()));

                tvCatalogfragmentKcjishu.setText("共" + courseDetail.getFiles().size() + "集");
                tvCatalogfragmentKctitle.setText(courseDetail.getKc_title());
                lvCatalogfragmentCoursecatalo.setOnItemClickListener(new MyOnItemClickListener());
                //加载页面完成  显示布局  发送消息
                postComplated(courseDetail.getFiles().get(1));
            }
        });
    }

    private void postComplated(CourseDetailBean.FilesBean filesBean) {
        Log.e(TAG, "postComplated: " + "加载完成事件"+filesBean.getFiles_title());
        bundle = new Bundle();
        bundle.putInt("flag", 0);
        bundle.putSerializable("file", filesBean);
        EventBus.getDefault().post(new DataLoadComplatedEvent(bundle));
    }

    private void postPlay(CourseDetailBean.FilesBean filesBean) {
        Log.e(TAG, "postPlay: " + "更换播放事件");

        bundle = new Bundle();
        bundle.putInt("flag", 4);

        bundle.putSerializable("file", filesBean);
        EventBus.getDefault().post(new DataLoadComplatedEvent(bundle));

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_catalog;
    }


    @OnClick({R.id.btn_catalogfragment_xuanji, R.id.btn_catalogfragment_cachedownload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_catalogfragment_xuanji:
                Toast.makeText(mContext, "选集pupupwindow", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_catalogfragment_cachedownload:
                Toast.makeText(mContext, "缓存下载", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    class MyAdapter extends BaseAdapter {

        private final List<CourseDetailBean.FilesBean> courses;

        public MyAdapter(List<CourseDetailBean.FilesBean> files) {
            courses = files;
        }

        @Override
        public int getCount() {
            return courses.size();
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
            ViewHolder holer;
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_lv_catalog_fragment, null);
                holer = new ViewHolder(view);
                view.setTag(holer);

            } else {
                holer = (ViewHolder) view.getTag();
            }

            if (courses.get(i).getFiles_url() != null && courses.get(i).getFiles_url() .length()>0){
                Log.e(TAG, "getView: "+courses.get(i).getFiles_url() );
                holer.btnShiting.setVisibility(View.VISIBLE);

            }else {
                holer.btnShiting.setVisibility(View.INVISIBLE);

            }
            if (currentPosition==i){
                holer.tvFilestitle.setTextColor(getResources().getColor(R.color.leftTextColorSelected));
            }else {
                holer.tvFilestitle.setTextColor(getResources().getColor(R.color.black));

            }
            holer.tvFilestitle.setText(courses.get(i).getFiles_title());
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_item_lv_catologfragment_filestitle)
            TextView tvFilestitle;
            @BindView(R.id.btn_item_lv_catologfragment_shiting)
            Button btnShiting;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            currentPosition = i;
            postPlay(courseDetail.getFiles().get(i));
        }
    }
}
