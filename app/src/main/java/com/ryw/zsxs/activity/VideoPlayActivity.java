package com.ryw.zsxs.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.CourseDetailBean;
import com.ryw.zsxs.bean.CourseListBean;
import com.ryw.zsxs.events.DataLoadComplatedEvent;
import com.ryw.zsxs.fragment.Catalog_Fragment;
import com.ryw.zsxs.fragment.Comment_Fragment;
import com.ryw.zsxs.fragment.Details_Fragment;
import com.ryw.zsxs.fragment.Recommend_Fragment;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.StringUtil;
import com.ryw.zsxs.utils.XutilsHttp;
import com.ryw.zsxs.view.ViewPagerIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
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

    @BindView(R.id.vp_videoplay)
    ViewPager vpVideoplay;
    //视频中间布局
    @BindView(R.id.rl_videoplay_center)
    RelativeLayout rlVideoplayCenter;
    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.iv_fragment_catalog_loading)
    ImageView ivFragmentCatalogLoading;


    private static final int MSG_UPDATE_SYSTIME = 1;
    private static final int MSG_UPDATE_POSITION = 2;
    private static final int MSG_HIDE_CONTROLLER = 3;
    private static final int MSG_UPDATE_BUFFER = 4;
    private static final int FAVORITE_RESULT = 5;

    private CourseListBean.CourseBean course;
    private String[] titles = {"目录", "详情", "推荐", "评论"};
    private ArrayList<BaseFragment> fragments;
    private final String kc_types ="0";
    private CourseDetailBean.FilesBean filesBean;
    private MediaPlayer myMP;
    private CourseDetailBean courseInfo;
    private String mssg = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HIDE_CONTROLLER:
                    break;
                case MSG_UPDATE_BUFFER:
                    break;
                case MSG_UPDATE_POSITION:
                    long currentPosition = videoview.getCurrentPosition();

                    sbVideoplayBottomProgress.setProgress((int) currentPosition);
                    //   Log.e(TAG, "handleMessage: "+videoview.getCurrentPosition() );
                    tvVideoplayBottomPlaynowtime.setText(StringUtil.formatDuration((int) currentPosition));

                    // handler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 500);
                    break;
                case FAVORITE_RESULT:
                    Toast.makeText(VideoPlayActivity.this, mssg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


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
        GetCourseInfo();

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.loading_rotate);
        animation.start();
        ivFragmentCatalogLoading.setAnimation(animation);


        setEnableOrNot(false);
        initSeekBar();
        initVideoview();
    }

    private void GetCourseInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("kc_types", "0");
        map.put("Action", "GetCourseInfo");
        map.put("kc_id", course.getKc_id());
        map.put("Acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        map.put("uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                courseInfo = gson.fromJson(result, CourseDetailBean.class);
                initViewPager();
            }
        });

    }

    private void initSeekBar() {
        sbVideoplayBottomProgress.setProgress(0);
        sbVideoplayBottomProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (myMP != null) {
                    myMP.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //初始化视频播放器
    private void initVideoview() {
        videoview.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, "onInfo: " + what + "   extra" + extra);
                return false;
            }
        });
        videoview.setOnPreparedListener(new playerPreparedListener());
        videoview.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (percent < 99) {
                    tvVideoplayCenterBufferpro.setVisibility(View.VISIBLE);
                    tvVideoplayCenterBufferpro.setText(percent + "");
                    setEnableOrNot(false);
                } else {
                    tvVideoplayCenterBufferpro.setVisibility(View.GONE);
                    setEnableOrNot(true);
                }
            }
        });
    }

    /**
     * 加载时 按钮不能使用
     */
    private void setEnableOrNot(boolean enable) {
        llVideoplayBottom.setEnabled(enable);
        rlVideoplayCenter.setEnabled(enable);
    }

    //初始化底下 的四个 fragment
    private void initViewPager() {
        fragments = new ArrayList<BaseFragment>();
        fragments.add(Catalog_Fragment.getInstance(course.getKc_id()));
        fragments.add(Details_Fragment.getInstance());
        fragments.add(Recommend_Fragment.getInstance());
        fragments.add(Comment_Fragment.getInstance());

        vpVideoplay.setAdapter(new MyPagerAdapter(getFragmentManager(), fragments));

        indicator.setViewPager(vpVideoplay);
        vpVideoplay.setCurrentItem(0);
        indicator.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    @OnClick({R.id.ib_videoplay_top_collect, R.id.ib_videoplay_top_share, R.id.ib_videoplay_top_menu, R.id.ib_videoplay_center_lock, R.id.ib_videoplay_center_play, R.id.ib_videoplay_bottom_playorpause, R.id.tv_videoplay_bottom_playnowtime, R.id.sb_videoplay_bottom_progress, R.id.ib_videoplay_bottom_fullscreenorsmalscreen})
    public void onViewClicked(View view) {
        //TODO   判断 是否登录 状态
        switch (view.getId()) {

            case R.id.ib_videoplay_top_collect:
                String kc_id = course.getKc_id();

                //     http://api.chinaplat.com/getval_2017?Action=SaveFavorite&cid=140706&acode=02c1c3dacbbc74b67f5190df5bbcc735&Uid=18733502093
                HashMap<String, String> map = new HashMap<>();
                int flag = 0;
                if (courseInfo.getShoucang().equals("0")) {
                    //0  没有收藏  收藏操作
                    map.put("Action", "SaveFavorite");
                    ibVideoplayTopCollect.setImageResource(R.mipmap.ic_collect_check);
                    flag = 0;
                } else {
                    map.put("Action", "DeleteFavorite");
                    flag = 1;
                    ibVideoplayTopCollect.setImageResource(R.mipmap.ic_collect_uncheck);

                    //  删除收藏 操作
                    //1  已经收藏

                }
                map.put("cid", kc_id);
                map.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
                map.put("Uid", Constant.USERNAME);
                final int finalFlag = flag;
                XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        if (result.contains("success")) {
                            //成功
                            if (finalFlag == 0) {
                                mssg = "添加收藏成功";
                            } else {
                                mssg = "取消收藏成功";

                            }

                        } else {
                            //失败
                            if (finalFlag == 0) {
                                mssg = "您还没收藏该课程";
                            } else {
                                mssg = "已收藏或购买，不用重新收";

                            }
                        }
                        handler.sendEmptyMessage(FAVORITE_RESULT);

                    }
                });


                break;
            case R.id.ib_videoplay_top_share:
                break;
            case R.id.ib_videoplay_top_menu:
                break;
            case R.id.ib_videoplay_center_lock:

                break;
            case R.id.ib_videoplay_center_play:
                long currentPosition = videoview.getCurrentPosition();
                if (currentPosition == 0) {
                    prePlayer(filesBean);
                    ibVideoplayCenterPlay.setVisibility(View.GONE);
                } else {
                    videoview.start();

                }
                handler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 500);


                break;
            case R.id.ib_videoplay_bottom_playorpause:
                if (videoview.isPlaying()) {
                    videoview.pause();
                    handler.removeMessages(MSG_UPDATE_POSITION);
                } else {

                    if (myMP == null)
                        prePlayer(filesBean);
                    else {
                        myMP.start();
                    }
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 500);

                }
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

        Bundle bundle = event.message;
        String flag = bundle.getString("flag");
        filesBean = (CourseDetailBean.FilesBean) bundle.getSerializable("file");
        if ("play".equals(flag)) {
            prePlayer(filesBean);
        } else {
            ivFragmentCatalogLoading.clearAnimation();
            ivFragmentCatalogLoading.setVisibility(View.GONE);
            vpVideoplay.setVisibility(View.VISIBLE);
        }

        setEnableOrNot(true);


    }

    /**
     * 准备播放视频
     *
     * @param filesBean
     */
    private void prePlayer(CourseDetailBean.FilesBean filesBean) {

        videoview.setVideoURI(Uri.parse(filesBean.getFiles_url()));
    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
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
    }

    //视频准备结束  开始播放
    private class playerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            myMP = mp;
            Log.e(TAG, "onPrepared: " + "playerPreparedListener");
            boolean isPrepared = true;
            //设置视频的时间
            long duration = mp.getDuration();
            sbVideoplayBottomProgress.setMax((int) duration);
            tvVideoplayBottomPlaycounttime.setText(StringUtil.formatDuration((int) duration));


        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
