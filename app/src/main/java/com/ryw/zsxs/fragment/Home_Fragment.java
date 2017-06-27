/*
 * Create on 2017-6-9 上午10:32
 * FileName: Home_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.ksdrActivity;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.GetZTBean;
import com.ryw.zsxs.bean.GetslidesBean;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yuzhenwei on 2017/6/9.
 * <p>
 * 首页的Fragment
 */

public class Home_Fragment extends BaseFragment {


    public static Home_Fragment instance = null;
    @BindView(R.id.layoutFooter)
    LinearLayout layoutFooter;
    @BindView(R.id.fragment_home_vp)
    ViewPager fragmentHomeVp;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;
    @BindView(R.id.home_first_ll)
    LinearLayout homeFirstLl;
    @BindView(R.id.home_tow_ll)
    LinearLayout homeTowLl;
    @BindView(R.id.home_three_ll)
    LinearLayout homeThreeLl;
    @BindView(R.id.home_four_ll)
    LinearLayout homeFourLl;
    @BindView(R.id.homefragment_rl_tuijian)
    RelativeLayout homefragmentRlTuijian;
    @BindView(R.id.home_gv)
    GridView homeGv;
    Unbinder unbinder;
    Unbinder unbinder1;


    private int preSelected = 0;//前一个选中点的位置
    private List<GetslidesBean.SlidesBean> slideslist;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //清空消息队列中的信息
            handler.removeCallbacksAndMessages(null);
            //轮播图自动切换到下一页
            fragmentHomeVp.setCurrentItem((fragmentHomeVp.getCurrentItem() + 1) % slideslist.size());
            //再次发送消息(循环)
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private List<GetZTBean.ListBean> picturelist;
    private GetZTBean getslidesBean;

    public static Home_Fragment getInstance() {
        if (instance == null) {
            instance = new Home_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //请求服务器的数据
        getDataFromeNet();

    }


    private void getDataFromeNet() {
        //借口地址
        String url = "http://api.chinaplat.com/getval_2017?Action=GetSlides";
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据
                parsedate(result);
            }
        });

    }

    private void parsedate(String result) {
        Gson gson = new Gson();
        GetslidesBean getslidesBean = gson.fromJson(result, GetslidesBean.class);
        //轮播图的数据
        slideslist = getslidesBean.getSlides();
        //填充轮播图数据
        fragmentHomeVp.setAdapter(new MyViewpagerAdapter());
        //轮播图的点击事件
        fragmentHomeVp.setOnPageChangeListener(new HomeViewpagerChangLister());
        //轮播图的点
        //密度比
        float density = getContext().getResources().getDisplayMetrics().density;

        for (int i = 0; i < slideslist.size(); i++) {
            ImageView iv_point = new ImageView(getContext());
            iv_point.setEnabled(false);
            iv_point.setBackgroundResource(R.drawable.viewpager_point_select);
            //宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (5 * density), (int) (5 * density));
            if (i != 0) {
                //点之间的间距
                params.leftMargin = (int) (10 * density);
            }
            iv_point.setLayoutParams(params);
            //添加到线性布局中
            homeLl.addView(iv_point);

        }
        //默认选中第0个点
        homeLl.getChildAt(0).setEnabled(true);
        //开始轮播图自动循环
        handler.sendEmptyMessageDelayed(0, 3000);


    }


    private void getDatainternet(int i) {
        String url = "http://api.chinaplat.com/getval_2017?Action=GetZT&Types=" + i;
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据2017
                parsedatell(result);
            }
        });

    }

    //2017数据的解析
    private void parsedatell(String result) {
        Gson gson = new Gson();
        GetZTBean getslidesBean = gson.fromJson(result, GetZTBean.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("getztBean", getslidesBean);
        startActivity(ksdrActivity.class, bundle);

    }


    //2017的点击事件

    @OnClick({R.id.home_first_ll, R.id.home_tow_ll, R.id.home_three_ll, R.id.home_four_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_first_ll:
                getDatainternet(1);
                break;
            case R.id.home_tow_ll:
                getDatainternet(2);
                break;
            case R.id.home_three_ll:
                getDatainternet(3);
                break;
            case R.id.home_four_ll:
                getDatainternet(4);
                break;


        }
    }


    class HomeViewpagerChangLister implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //当页面选中的时候点变为红色
            homeLl.getChildAt(position).setEnabled(true);
            //吧前一个选中的点重新改为白色
            homeLl.getChildAt(preSelected).setEnabled(false);
            preSelected = position;


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyViewpagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getContext());
            //显示默认图片
            iv.setImageResource(R.mipmap.huandengpian);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String picurl = slideslist.get(position).getPic();
            XutilsHttp.getInstance().bindCommonImage(iv, picurl, false);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return slideslist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    /**
     * 这代码留着  等界面看不见的时候停止发消息 让轮播图停下来
     */
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
