package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.fragment.Base_Fragment;
import com.ryw.zsxs.fragment.Kecheng_Fragment;
import com.ryw.zsxs.fragment.Xiangqing_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr_Shadow on 2017/6/25.
 * 音频中心点击条目进去的音频详情
 */

public class AudioCenterDetailActivity extends FragmentActivity {
    @BindView(R.id.rb_xiangqing)
    RadioButton rbXiangqing;
    @BindView(R.id.rb_kecheng)
    RadioButton rbKecheng;
    @BindView(R.id.gv_audio)
    RadioGroup gvAudio;
    @BindView(R.id.vp_audio)
    ViewPager vpAudio;
    private ArrayList<Base_Fragment> list;
    private ArrayList<RadioButton> rblist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        init();
    }

    public void init() {
        rblist = new ArrayList<>();
        rblist.add(rbXiangqing);
        rblist.add(rbKecheng);
        list = new ArrayList<>();
        //
        list.add(new Xiangqing_Fragment());
        list.add(new Kecheng_Fragment());
        Intent  intent = getIntent();
        Bundle bundle = intent.getExtras();
        //获取到的课程详细
        CourseListBean.CourseBean data = (CourseListBean.CourseBean) bundle.getSerializable("data");
        initData();
    }


    private void initData() {
        gvAudio.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        vpAudio.setAdapter(new MyAdapter(getSupportFragmentManager(),list));
        vpAudio.setOnPageChangeListener(new MyOnPageChangeListener());
    }
class MyAdapter extends FragmentPagerAdapter{
    private List<Base_Fragment> mlist;

    public MyAdapter(FragmentManager fm, ArrayList<Base_Fragment> list) {
        super(fm);
        this.mlist=list;
    }

    @Override
    public Fragment getItem(int position) {

        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            rblist.get(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                vpAudio.setCurrentItem(i);
        }
    }
}
