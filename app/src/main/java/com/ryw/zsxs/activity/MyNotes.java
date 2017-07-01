package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.ryw.zsxs.bean.MyNotesBean;

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
 * 个人中心------我的笔记
 */

public class MyNotes extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mynotes_rb_shipin)
    RadioButton mynotesRbShipin;
    @BindView(R.id.mynotes_rb_yinpin)
    RadioButton mynotesRbYinpin;
    @BindView(R.id.mynotes_rb_dushu)
    RadioButton mynotesRbDushu;

    @BindView(R.id.mynotes_vp)
    MyViewpager mynotesVp;
    private ArrayList<RadioButton> mynotes_rb;
    private List<MyNotesBean.CourselistBean> course;

    private MyPagerAdapter myPagerAdapter;

    private MyNotesLvAdapter mynotesLvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.mynotes;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initEvent() {
        mynotesRbShipin.setOnClickListener(this);
        mynotesRbYinpin.setOnClickListener(this);
        mynotesRbDushu.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initData() {
        mynotes_rb = new ArrayList<>();
        mynotes_rb.add(mynotesRbShipin);
        mynotes_rb.add(mynotesRbYinpin);
        mynotes_rb.add(mynotesRbDushu);

        FromNet(0);//从网络获取数据
        mynotesVp.setOnPageChangeListener(new ViewpagerChangeListener());//viewpager的监听

    }

    private void FromNet(int i) {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getNotesCourse");
        hashmap.put("acode", SpUtils.getString(mContext,LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext,LoginAcitvity.USERNAME));
//        hashmap.put("acode", "280d546cc83ab2140127b3a09b0ee265");
//        hashmap.put("Uid", "18733513882");

        hashmap.put("Kc_types", i+"");//
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                MyNotesBean myNotesBean = gson.fromJson(result, MyNotesBean.class);
                course = myNotesBean.courselist;
                if (myPagerAdapter == null) {

                    mynotesLvAdapter = new MyNotesLvAdapter();


                    myPagerAdapter = new MyPagerAdapter();
                    mynotesVp.setAdapter(myPagerAdapter);
                } else {
                    mynotesLvAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.mynotes_rb_shipin:
                mynotesVp.setCurrentItem(0);
                FromNet(0);
                break;
            case R.id.mynotes_rb_yinpin:
                mynotesVp.setCurrentItem(1);
                FromNet(1);
                break;
            case R.id.mynotes_rb_dushu:
                mynotesVp.setCurrentItem(2);
                FromNet(2);
                break;

        }
    }


    /*viewpager的页面改变监听，用来控制radiobutton的选中状态*/
    private class ViewpagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mynotes_rb.get(position).setChecked(true);
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

            return mynotes_rb.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.mynotes_viewpager_item, null);//viewpager填充的布局，为listview
            ListView mynotes_lv = view.findViewById(R.id.mynotes_lv);

            mynotes_lv.setAdapter(mynotesLvAdapter);


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }


    }

    class MyNotesLvAdapter extends BaseAdapter {//listview的数据填充

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
                view = View.inflate(mContext, R.layout.item_mynotes_lv, null);//listveiw条目的布局
                holder = new MyNotes.MyNotesLvAdapter.ViewHolder();

                holder.mynotes_kechengtu = view.findViewById(R.id.mynotes_kechengtu);
                holder.mynotes_info = view.findViewById(R.id.mynotes_info);
                holder.mynotes_chakan_btn = view.findViewById(R.id.mynotes_chakan_btn);
                holder.mynotes_countnotes = view.findViewById(R.id.mynotes_countnotes);
                holder.mynotes_chakan_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,"查看课程视频",Toast.LENGTH_LONG).show();
                    }
                });
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (MyNotes.MyNotesLvAdapter.ViewHolder) view.getTag();
            }

            XutilsHttp.getInstance().bindCommonImage(holder.mynotes_kechengtu, course.get(position).img, true);

            holder.mynotes_info.setText(course.get(position).info);
            holder.mynotes_countnotes.setText("共"+course.get(position).notescounts+"条笔记");



            return view;
        }

        class ViewHolder {
            public ImageView mynotes_kechengtu;
            public TextView mynotes_info,mynotes_countnotes;
            public Button mynotes_chakan_btn;
        }
    }
}
