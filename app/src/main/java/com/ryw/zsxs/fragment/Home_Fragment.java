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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.activity.T20172Activity;
import com.ryw.zsxs.activity.ksdrActivity;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.GetCourse100;
import com.ryw.zsxs.bean.GetZTBean;
import com.ryw.zsxs.bean.GetZTShow;
import com.ryw.zsxs.bean.GetslidesBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    Unbinder unbinder2;
    @BindView(R.id.home_baifenbi_iv1)
    ImageView homeBaifenbiIv1;
    @BindView(R.id.home_baifenbi_tv1)
    TextView homeBaifenbiTv1;
    @BindView(R.id.home_baifenbi_iv2)
    ImageView homeBaifenbiIv2;
    @BindView(R.id.home_baifenbi_tv2)
    TextView homeBaifenbiTv2;
    @BindView(R.id.home_baifenbi_iv3)
    ImageView homeBaifenbiIv3;
    @BindView(R.id.home_baifenbi_tv3)
    TextView homeBaifenbiTv3;
    Unbinder unbinder3;
    Unbinder unbinder4;

    private String text2017 = " ";
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
        initdata();
        initevent();


    }

    private void initevent() {

    }

    private void initdata() {
        //请求服务器的数据轮播图
        getDataFromeNet();
        //请求服务器百分比课程信息
        getdatabaifenbi();


    }

    public void getdatabaifenbi() {
        //借口地址
        String url = "http://api.chinaplat.com/getval_2017?Action=GetCourse100";
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据百分比课程
                parsedatel2(result);
            }


        });
    }

    private void parsedatel2(String result) {
        Gson gson = new Gson();
        GetCourse100 getCourse100 = gson.fromJson(result, GetCourse100.class);
        List<GetCourse100.CourseBean> getcourse100list = getCourse100.getCourse();
        XutilsHttp.getInstance().bindCommonImage(homeBaifenbiIv1, getcourse100list.get(0).getImg(), false);
        XutilsHttp.getInstance().bindCommonImage(homeBaifenbiIv2, getcourse100list.get(1).getImg(), false);
        XutilsHttp.getInstance().bindCommonImage(homeBaifenbiIv3, getcourse100list.get(2).getImg(), false);
        homeBaifenbiTv1.setText(getcourse100list.get(0).getTitle());
        homeBaifenbiTv2.setText(getcourse100list.get(1).getTitle());
        homeBaifenbiTv3.setText(getcourse100list.get(2).getTitle());
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
        float density = mContext.getResources().getDisplayMetrics().density;

        for (int i = 0; i < slideslist.size(); i++) {
            ImageView iv_point = new ImageView(mContext);
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
        final String url = "http://api.chinaplat.com/getval_2017?Action=GetZT&Types=" + i;
        XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                //解析数据2017
                parsedatell(result,url);
            }
        });

    }

    //2017数据的解析
    private void parsedatell(String result,String url) {
        Gson gson = new Gson();
        GetZTBean getslidesBean = gson.fromJson(result, GetZTBean.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("title", text2017);
        bundle.putSerializable("getztBean", getslidesBean);
        startActivity(ksdrActivity.class, bundle);

    }


    //2017的点击事件

    @OnClick({R.id.home_first_ll, R.id.home_tow_ll, R.id.home_three_ll, R.id.home_four_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_first_ll:
                text2017 = "考试达人";
                getDatainternet(1);
                break;
            case R.id.home_tow_ll:
                text2017 = "职场精英";
                getDatainternet(2);
                break;
            case R.id.home_three_ll:
                text2017 = "生活易趣";
                getDatainternet(3);
                break;
            case R.id.home_four_ll:
                text2017 = "企业培养";
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
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView iv = new ImageView(mContext);
            //显示默认图片
            iv.setImageResource(R.mipmap.huandengpian);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String picurl = slideslist.get(position).getPic();
            XutilsHttp.getInstance().bindCommonImage(iv, picurl, false);
            container.addView(iv);
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //判断type
                    if (slideslist.get(position).getPictype().equals("app")){


                    //图片的点击事件
                    String url="http://api.chinaplat.com/getval_2017?Action=getZTShow&Types="+slideslist.get(position).getPictype()+"&ztid="+slideslist.get(position).getPicURL();
                    XutilsHttp.getInstance().get(url, null, new XutilsHttp.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            //解析数据轮播图
                            parsedatel3(result);

                        }
                    });
                    }else{
                        String picURL = slideslist.get(position).getPicURL()+"?acode="+SpUtils.getString(mContext, LoginAcitvity.ACODE)+"&uid="+SpUtils.getString(mContext, LoginAcitvity.USERNAME);
                        Bundle bundle = new Bundle();
                        bundle.putString("url",picURL);



                    }
                }
            });
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

    private void parsedatel3(String result) {
        Gson gson = new Gson();
        GetZTShow getZTShowBean = gson.fromJson(result, GetZTShow.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("getZTShowBean",getZTShowBean);
        startActivity(T20172Activity.class,bundle);


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    /**
     * 这代码留着  等界面看不见的时候停止发消息 让轮播图停下来
     */
//    @Override
//    public void onPause() {
//        super.onPause();
//        handler.removeCallbacksAndMessages(null);
//    }
}
