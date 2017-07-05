package com.ryw.zsxs.activity;

import android.content.Intent;
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
import com.ryw.zsxs.bean.CourseBean;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.bean.MyNotesBean;
import com.ryw.zsxs.bean.MyProblemBean;
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
 * 个人中心-------课程答疑
 */

public class MyProblem extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myproblem_rb_shipin)
    RadioButton myproblemRbShipin;
    @BindView(R.id.myproblem_rb_yinpin)
    RadioButton myproblemRbYinpin;
    @BindView(R.id.myproblem_rb_dushu)
    RadioButton myproblemRbDushu;
    @BindView(R.id.myproblem_rb_wenzhang)
    RadioButton myproblemRbWenzhang;
    @BindView(R.id.rg_myproblem)
    RadioGroup rgMyproblem;
    @BindView(R.id.myproblem_vp)
    MyViewpager myproblemVp;
    private ArrayList<RadioButton> myproblem_rb;
    private List<MyProblemBean.ListBean> course;
    private MyProblem.MyPagerAdapter myPagerAdapter;

    private MyProblem.MyProblemLvAdapter myProblemLvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_myproblem;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    private void initEvent() {
        myproblemRbShipin.setOnClickListener(this);
        myproblemRbYinpin.setOnClickListener(this);
        myproblemRbDushu.setOnClickListener(this);
        myproblemRbWenzhang.setOnClickListener(this);
        back.setOnClickListener(this);


    }

    private void initData() {
        myproblem_rb = new ArrayList<>();
        myproblem_rb.add(myproblemRbShipin);
        myproblem_rb.add(myproblemRbYinpin);
        myproblem_rb.add(myproblemRbDushu);
        myproblem_rb.add(myproblemRbWenzhang);
        FromNet(0);//从网络获取数据
        myproblemVp.setOnPageChangeListener(new MyOnPageChangeListener());//viewpager的监听
    }

    private void FromNet(int i) {//从网上获取数据
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getMyQuestion");
         hashmap.put("acode", SpUtils.getString(mContext,LoginAcitvity.ACODE));
        hashmap.put("Uid",SpUtils.getString(mContext,LoginAcitvity.USERNAME));
//        hashmap.put("acode", "280d546cc83ab2140127b3a09b0ee265");//这里以后需要改的
//        hashmap.put("Uid", "18733513882");
        hashmap.put("Kc_types", i + "");
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                MyProblemBean myProblemBean = gson.fromJson(result, MyProblemBean.class);

                course = myProblemBean.list;

                if (course.size() == 0) {
                    //TODO 有问题
                    Toast.makeText(mContext, "你还没有答疑喔", Toast.LENGTH_LONG).show();
                }

                if (myPagerAdapter == null) {

                    myProblemLvAdapter = new MyProblem.MyProblemLvAdapter();

                    myPagerAdapter = new MyProblem.MyPagerAdapter();
                    myproblemVp.setAdapter(myPagerAdapter);
                } else {
                    myProblemLvAdapter.notifyDataSetChanged();
                }


            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rb_shipin:
                myproblemVp.setCurrentItem(0);
                FromNet(0);

                break;
            case R.id.rb_yinpin:
                myproblemVp.setCurrentItem(1);

                FromNet(1);
                break;
            case R.id.rb_dushu:
                myproblemVp.setCurrentItem(2);

                FromNet(2);
                break;
            case R.id.rb_wenzhang:
                myproblemVp.setCurrentItem(3);

                FromNet(3);
                break;

            case R.id.back:
                finish();
                break;

        }
    }


    //viewpager页面滑动的监听，用来实现上面按钮的选择
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            myproblem_rb.get(position).setChecked(true);

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

            return myproblem_rb.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.myproblem_viewpager_item, null);//viewpager填充的布局，为listview
            ListView myproblem_lv = view.findViewById(R.id.myproblem_lv);

            myproblem_lv.setAdapter(myProblemLvAdapter);


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }


    }

    class MyProblemLvAdapter extends BaseAdapter {//listview的数据填充

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            MyProblem.MyProblemLvAdapter.ViewHolder holder = null;
            View view;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.item_myproblem_lv, null);//listveiw条目的布局
                holder = new ViewHolder();

                holder.myproblem_lv_item_problem = view.findViewById(R.id.myproblem_lv_item_problem);
                holder.myproblem_lv_item_answer = view.findViewById(R.id.myproblem_lv_item_answer);
                holder.chakancourse = view.findViewById(R.id.chakancourse);
                holder.chakancourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*CourseListBean courseBean = new CourseListBean();


                        Bundle bundle = new Bundle();

                        bundle.putSerializable("data", courseBean.getCourse().get(position-1));

                        startActivity(VideoPlayActivity.class,bundle);*/


                    }
                });
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (MyProblem.MyProblemLvAdapter.ViewHolder) view.getTag();
            }

            //XutilsHttp.getInstance().bindCommonImage(holder.iv_item_xuankedetail_pv, course.get(position).img, true);

            holder.myproblem_lv_item_problem.setText(course.get(position).question);
            String replay = course.get(position).replay;
            if (!TextUtils.isEmpty(replay)){
                holder.myproblem_lv_item_answer.setText(course.get(position).replay);
            }else {

            }



            return view;
        }

        class ViewHolder {

            public TextView myproblem_lv_item_problem,myproblem_lv_item_answer;
            public Button chakancourse;
        }
    }
}