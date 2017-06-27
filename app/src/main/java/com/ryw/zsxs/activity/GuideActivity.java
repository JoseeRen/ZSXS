/*
 * Create on 2017-6-8 下午6:05
 * FileName: GuideActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.SharedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    @BindView(R.id.ll_guide)
    LinearLayout llGuide;
    @BindView(R.id.iv_red_point)
    ImageView ivRedPoint;
    private ArrayList<ImageView> imageList;
    private int distence;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_guide;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initEvent();
    }


    private void initEvent() {
        //设置viewpager的监听
        //
//        vp_guide.addOnPageChangeListener();
//        vp_guide.removeOnPageChangeListener();
        //  vp_guide.clearOnPageChangeListeners();
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //根据页面的滑动，设置红点的左边距
                //红点移动的距离=两个白点的间距*viewpager移动的比例
                // measure  ---layout --- draw

                int leftMarign = (int) (distence * positionOffset) + distence * position;
                //Log.e("huida","页面移动：："+positionOffset);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();

                params.leftMargin = leftMarign;

                ivRedPoint.setLayoutParams(params);


            }

            @Override
            public void onPageSelected(int position) {
                //如果选中的是最后一个页面，button显示
                if (position == 2) {
                    btnGuide.setVisibility(View.VISIBLE);
                } else {
                    //其他页面  隐藏
                    btnGuide.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //为了确保肯定能拿到坐标，设置全局布局的监听
        //通过视图树观察者完成
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            /**
             * 当全局的每一个控件开始layout的时候会调用
             */
            @Override
            public void onGlobalLayout() {
                //根据坐标计算白点的间距
                distence = llGuide.getChildAt(1).getLeft() - llGuide.getChildAt(0).getLeft();

                //该方法不需要多次调用，获取到间距后，移除监听
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }


    private void initData() {
        //维护viewpager图片的数组
        int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};

        imageList = new ArrayList<>();

        //密度比
        float density = getResources().getDisplayMetrics().density;

        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);

            imageList.add(iv);

            //添加默认的点
            ImageView iv_point = new ImageView(this);
            iv_point.setBackgroundResource(R.drawable.point_normal);
            //代码中定义控件的大小，单位是px
            //px=dp*密度比
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (10 * density), (int) (10 * density));

            if (i != 0) {

                params.leftMargin = (int) (15 * density);

            }

            iv_point.setLayoutParams(params);
if (llGuide==null){
    Log.e("dsfaffs",">>>>>>>>>>>>>>>>>>>>>>>");
}
            llGuide.addView(iv_point);

        }

        //给viewpager填充数据
        vpGuide.setAdapter(new GuideAdapter());


        //给viewpager添加动画
        //  vp_guide.setPageTransformer(true,new DepthPageTransformer());


    }


    @OnClick(R.id.btn_guide)
    public void onViewClicked() {
        //点击跳转到主页面
        startActivity(MainActivity.class, null);
        finish();

        //向sp中存储标记
        SharedPreferences.getInstance().putBoolean("first_boot", false);
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将条目的view添加到container中
            ImageView iv = imageList.get(position);
            container.addView(iv);

            //将条目的view返回
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
