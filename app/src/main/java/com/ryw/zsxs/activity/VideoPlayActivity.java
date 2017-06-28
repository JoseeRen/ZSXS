package com.ryw.zsxs.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ryw.zsxs.R;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.fragment.Catalog_Fragment;
import com.ryw.zsxs.fragment.Comment_Fragment;
import com.ryw.zsxs.fragment.Details_Fragment;
import com.ryw.zsxs.fragment.Recommend_Fragment;
import com.ryw.zsxs.view.ViewPagerIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Mr_Shadow on 2017/6/25.
 * 视频播放界面
 * <p>
 * //先初始化目录
 * 再初始化视频
 */

public class VideoPlayActivity extends BaseActivity {
    String url = "http://121.22.11.84:8008/12703/V/20160110/2016011001051152027.mp4";
    //播放器
    @BindView(R.id.videoview)
    VideoView videoview;

    //控制器顶部视频名
    @BindView(R.id.tv_videoplay_top_title)
    TextView tvVideoplayTopTitle;
    //控制器 收藏
    @BindView(R.id.ib_videoplay_top_collect)
    ImageButton ibVideoplayTopCollect;
    //控制器分享
    @BindView(R.id.ib_videoplay_top_share)
    ImageButton ibVideoplayTopShare;
    //控制器  菜单
    @BindView(R.id.ib_videoplay_top_menu)
    ImageButton ibVideoplayTopMenu;
    //控制 器电量
    @BindView(R.id.iv_videolpay_batter)
    ImageView ivVideolpayBatter;
    //控制器  当前时间
    @BindView(R.id.tv_videoplay_nowtime)
    TextView tvVideoplayNowtime;
    //控制器 右边的电量和时间布局
    @BindView(R.id.ll_videoplay_top_right)
    LinearLayout llVideoplayTopRight;
    //整个控制器上部  布局
    @BindView(R.id.ll_videoplay_top)
    LinearLayout llVideoplayTop;
    //控制器蹭的锁定屏幕
    @BindView(R.id.ib_videoplay_center_lock)
    ImageButton ibVideoplayCenterLock;
    //控制器蹭的播放
    @BindView(R.id.ib_videoplay_center_play)
    ImageButton ibVideoplayCenterPlay;
    //控制器中间视频正在缓冲
    @BindView(R.id.iv_videoplay_center_buffer)
    ImageView ivVideoplayCenterBuffer;
    //控制器中间视频缓冲进度
    @BindView(R.id.tv_videoplay_center_bufferpro)
    TextView tvVideoplayCenterBufferpro;
    //控制器底部播放视频或暂停
    @BindView(R.id.ib_videoplay_bottom_playorpause)
    ImageButton ibVideoplayBottomPlayorpause;
    //控制器询问 当前播放的时间
    @BindView(R.id.tv_videoplay_bottom_playnowtime)
    TextView tvVideoplayBottomPlaynowtime;
    //控制器的 进度条
    @BindView(R.id.sb_videoplay_bottom_progress)
    SeekBar sbVideoplayBottomProgress;
    //控制器询问的视频时长
    @BindView(R.id.tv_videoplay_bottom_playcounttime)
    TextView tvVideoplayBottomPlaycounttime;
    //控制器  全屏非全屏
    @BindView(R.id.ib_videoplay_bottom_fullscreenorsmalscreen)
    ImageButton ibFullscreenorsmalscreen;
    //底部布局 的根
    @BindView(R.id.ll_videoplay_bottom)
    LinearLayout llVideoplayBottom;
    /* //页签指示器
     @BindView(R.id.vp_tab_videoplay)
     PagerTabStrip vpTabVideoplay;*/
    //页签
    @BindView(R.id.vp_videoplay)
    ViewPager vpVideoplay;
    //视频中间布局
    @BindView(R.id.rl_videoplay_center)
    RelativeLayout rlVideoplayCenter;
    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;

    private CourseListBean.CourseBean course;
    private String[] titles = {"目录", "详情", "推荐", "评论"};
    private ArrayList<BaseFragment> fragments;
    private String kc_types = "0";

    @Override
    public int getContentViewResId() {
        return R.layout.activity_videoplay;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        //初始化视频播放
        Vitamio.isInitialized(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        //获取到的课程详细
        course = (CourseListBean.CourseBean) bundle.getSerializable("data");
        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        initFragment();

    }

    private void initFragment() {
        fragments = new ArrayList<BaseFragment>();
        fragments.add(Catalog_Fragment.getInstance(course.getKc_id()));
        fragments.add(Details_Fragment.getInstance());
        fragments.add(Recommend_Fragment.getInstance());
        fragments.add(Comment_Fragment.getInstance());

        vpVideoplay.setAdapter(new MyPagerAdapter(getFragmentManager(), fragments));

        indicator.setViewPager(vpVideoplay);
        vpVideoplay.setCurrentItem(0);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @OnClick({R.id.ib_videoplay_top_collect, R.id.ib_videoplay_top_share, R.id.ib_videoplay_top_menu, R.id.ib_videoplay_center_lock, R.id.ib_videoplay_center_play, R.id.ib_videoplay_bottom_playorpause, R.id.tv_videoplay_bottom_playnowtime, R.id.sb_videoplay_bottom_progress, R.id.ib_videoplay_bottom_fullscreenorsmalscreen})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ib_videoplay_top_collect:
                break;
            case R.id.ib_videoplay_top_share:
                break;
            case R.id.ib_videoplay_top_menu:
                break;
            case R.id.ib_videoplay_center_lock:
                break;
            case R.id.ib_videoplay_center_play:
                break;
            case R.id.ib_videoplay_bottom_playorpause:
                break;
            case R.id.tv_videoplay_bottom_playnowtime:
                break;
            case R.id.sb_videoplay_bottom_progress:
                break;
            case R.id.ib_videoplay_bottom_fullscreenorsmalscreen:
                break;
        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter {


        private final ArrayList<BaseFragment> fragments;

        public MyPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /**
     * 注册订阅者
     *
     * @param event
     */
    @Subscribe
    public void onDataLoadComplated(DataLoadComplatedEvent event) {
        Log.e(TAG, "onDataLoadComplated: " + "收到事件完成");

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
