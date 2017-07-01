package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.bean.MyCollectBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.MyViewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhao on 2017/6/24.
 * 我的收藏页面
 */

public class MyCollect extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.rb_shipin)
    RadioButton rbShipin;
    @BindView(R.id.rb_yinpin)
    RadioButton rbYinpin;
    @BindView(R.id.rb_dushu)
    RadioButton rbDushu;
    @BindView(R.id.rb_wenzhang)
    RadioButton rbWenzhang;
    @BindView(R.id.rg_mycollect)
    RadioGroup rgMycollect;
    @BindView(R.id.mycollect_vp)
    MyViewpager mycollectVp;
    @BindView(R.id.back)
    ImageView back;
    private ArrayList<RadioButton> rb;
    private List<MyCollectBean.CourseBean> course;

    private MyPagerAdapter myPagerAdapter;
    private MyPagerAdapter myPagerAdapter1;
    private MyCollectLvAdapter myCollectLvAdapter;

    public int getContentViewResId() {
        return R.layout.activity_mycollect;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initEvent() {
        //点击事件
        rbShipin.setOnClickListener(this);
        rbYinpin.setOnClickListener(this);
        rbDushu.setOnClickListener(this);
        rbWenzhang.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    private void initData() {
        //rb为数据，范型为radiobutton，是用来给设置radiobutton选中的
        rb = new ArrayList<>();
        rb.add(rbShipin);
        rb.add(rbYinpin);
        rb.add(rbDushu);
        rb.add(rbWenzhang);


        FromNet(0);//从网络获取数据

        mycollectVp.setOnPageChangeListener(new MyOnPageChangeListener());//viewpager的监听


    }

    private void FromNet(int i) {

        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getFavorite");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        //hashmap.put("acode", "280d546cc83ab2140127b3a09b0ee265");//这里以后需要改的
        //hashmap.put("Uid", "18733513882");
        hashmap.put("types", i + "");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                MyCollectBean myCollectBean = gson.fromJson(result, MyCollectBean.class);
                course = myCollectBean.Course;
                if (course == null) {
                    //TODO 有问题
                    Toast.makeText(mContext, "你还没有收藏喔", Toast.LENGTH_LONG).show();
                }
                if (myPagerAdapter == null) {

                    myCollectLvAdapter = new MyCollectLvAdapter();


                    myPagerAdapter = new MyPagerAdapter();
                    mycollectVp.setAdapter(myPagerAdapter);

                } else {
                    myCollectLvAdapter.notifyDataSetChanged();

                }


            }
        });


    }

    @Override
    public void onClick(View view) {
        //radiobutton的点击事件，和viewpager滑动是结合的
        switch (view.getId()) {
            case R.id.rb_shipin:
                mycollectVp.setCurrentItem(0);
                FromNet(0);

                break;
            case R.id.rb_yinpin:
                mycollectVp.setCurrentItem(1);

                FromNet(1);
                break;
            case R.id.rb_dushu:
                mycollectVp.setCurrentItem(2);

                FromNet(2);
                break;
            case R.id.rb_wenzhang:
                mycollectVp.setCurrentItem(3);

                FromNet(3);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    //viewpager页面滑动的监听，用来实现上面按钮的选择
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            rb.get(position).setChecked(true);

            FromNet(position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //viewpager的填充
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            Log.e(TAG, "getCount: " + rb.size());
            return rb.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.mycollect_viewpager_item, null);
            ListView mycollect_lv = view.findViewById(R.id.mycollect_lv);

            mycollect_lv.setAdapter(myCollectLvAdapter);


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);


        }


    }

    class MyCollectLvAdapter extends BaseAdapter {//listview的监听

        @Override
        public int getCount() {
            return course.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            View view;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.item_xuankedetail_pvlistview, null);
                holder = new ViewHolder();
                holder.iv_item_xuankedetail_pv = view.findViewById(R.id.iv_item_xuankedetail_pv);
                holder.tv_title_item_xuankedetail_pv = view.findViewById(R.id.tv_title_item_xuankedetail_pv);
                holder.tv_keshi_item_xuankedetail_pv = view.findViewById(R.id.tv_keshi_item_xuankedetail_pv);
                holder.tv_jifen_item_xuankedetail_pv = view.findViewById(R.id.tv_jifen_item_xuankedetail_pv);
                holder.tv_info_item_xuankedetail_pv = view.findViewById(R.id.tv_info_item_xuankedetail_pv);
                holder.tv_dianjiliang_item_xuankedetail_pv = view.findViewById(R.id.tv_dianjiliang_item_xuankedetail_pv);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            XutilsHttp.getInstance().bindCommonImage(holder.iv_item_xuankedetail_pv, course.get(position).img, true);
            holder.tv_title_item_xuankedetail_pv.setText(course.get(position).title);
            holder.tv_keshi_item_xuankedetail_pv.setText(course.get(position).keshi + "");
            holder.tv_info_item_xuankedetail_pv.setText(course.get(position).info);
            holder.tv_jifen_item_xuankedetail_pv.setText(course.get(position).money + "");
            holder.tv_dianjiliang_item_xuankedetail_pv.setText(course.get(position).hot + "");


            return view;
        }

        class ViewHolder {
            public ImageView iv_item_xuankedetail_pv;
            public TextView tv_title_item_xuankedetail_pv, tv_info_item_xuankedetail_pv, tv_keshi_item_xuankedetail_pv, tv_jifen_item_xuankedetail_pv, tv_dianjiliang_item_xuankedetail_pv;


        }
    }


}
