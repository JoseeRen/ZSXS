/*
 * Create on 2017-6-9 上午10:34
 * FileName: User_Fragment.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryw.zsxs.R;
import com.ryw.zsxs.activity.AboutZX;
import com.ryw.zsxs.activity.CursorZhengDing;
import com.ryw.zsxs.activity.Help;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.activity.MyCollect;
import com.ryw.zsxs.activity.MyJiFen;
import com.ryw.zsxs.activity.MyNotes;
import com.ryw.zsxs.activity.MyProblem;
import com.ryw.zsxs.activity.MyTest;
import com.ryw.zsxs.activity.Setting;
import com.ryw.zsxs.activity.UserAccountActivity;
import com.ryw.zsxs.activity.UserJifenActivity;
import com.ryw.zsxs.activity.UserLoginMessageActivity;
import com.ryw.zsxs.activity.UserMessageActivity;
import com.ryw.zsxs.activity.UserShareActivity;
import com.ryw.zsxs.activity.UserXuebiActivity;
import com.ryw.zsxs.activity.UserXueshiActivity;
import com.ryw.zsxs.activity.ZuZhiAndSchool;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseFragment;
import com.ryw.zsxs.bean.UserInfoBean;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.Unbinder;

//import com.ryw.zsxs.activity.MyNotes;

/**
 * Created by Mr_Shadow on 2017/6/9.
 * 个人中心
 * <p>
 * 注意有两个 方法 不要复写
 */

public class User_Fragment extends BaseFragment implements View.OnClickListener {


    public static User_Fragment instance = null;
    @BindView(R.id.layoutFooter)
    RelativeLayout layoutFooter;
    Unbinder unbinder;
    @BindView(R.id.tv_user_title)
    TextView tvUserTitle;
    @BindView(R.id.iv_user_usermessage)
    ImageView ivUserUsermessage;
    @BindView(R.id.iv_user_login)
    ImageView ivUserLogin;
    @BindView(R.id.ll_user_jifen)
    LinearLayout llUserJifen;
    @BindView(R.id.ll_user_xuebi)
    LinearLayout llUserXuebi;
    @BindView(R.id.ll_user_xueshi)
    LinearLayout llUserXueshi;
    @BindView(R.id.ll_user_message)
    LinearLayout llUserMessage;
    @BindView(R.id.ll_user_share)
    LinearLayout llUserShare;
    @BindView(R.id.ll_user_account)
    LinearLayout llUserAccount;
    @BindView(R.id.lv_user)
    ListView lvUser;
    @BindView(R.id.btn_user_back)
    Button btnUserBack;
    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;
    Unbinder unbinder1;
    View view;
    @BindView(R.id.tv_user_myjifen)
    TextView tvUserMyjifen;
    @BindView(R.id.tv_user_myxuebi)
    TextView tvUserMyxuebi;
    @BindView(R.id.tv_user_myxueshi)
    TextView tvUserMyxueshi;
    private Intent userlogin_intent;

    // 存放个人中心listview的图片
    int[] userlist_icon = {0, R.mipmap.my_center_wdsc,
            R.mipmap.my_center_kcdy,
            R.mipmap.my_center_wdbj,
            R.mipmap.my_center_kczd,
            5, R.mipmap.my_center_jfsc,
            R.mipmap.my_center_wdcy,
            R.mipmap.zuzhi_icon,
            9, R.mipmap.my_center_bzyfk,
            R.mipmap.my_center_sz,
            12, R.mipmap.my_center_gyzs};
    // 存放个人中心listview的标题
    String[] userlist_title = {"0", "我的收藏", "课程答疑", "我的笔记", "课程征订",
            "5", "积分商城", "考试中心", "组织&学校",
            "9", "帮助与反馈", "设置",
            "12", "关于中仕"};


    public static User_Fragment getInstance() {
        if (instance == null) {
            instance = new User_Fragment();
        }
        return instance;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initData();
        initEvent();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取中仕个人中心的数据
        getUsertopMessage();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // listview 设置视频器
        lvUser.setAdapter(new MyUserListAdapter());
        setListViewHeightBasedOnChildren(lvUser);
    }

    /**
     * 获取中仕个人中心 用户名，头像，我的积分，我的学币，我的学识
     */
    private void getUsertopMessage() {
        final ImageOptions options = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setIgnoreGif(true)
                .build();
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("Action", "getUserInfo");
        hashmap.put("acode", SpUtils.getString(mContext, LoginAcitvity.ACODE));
        hashmap.put("Uid", SpUtils.getString(mContext, LoginAcitvity.USERNAME));
        XutilsHttp.getInstance().get(Constant.HOSTNAME, hashmap, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: " + result);
                Gson gson = new Gson();
                UserInfoBean userInfoBean = gson.fromJson(result, UserInfoBean.class);
                x.image().loadDrawable(userInfoBean.Pic, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        ivUserUsermessage.setImageDrawable(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                tvUserTitle.setText(userInfoBean.Nicename);
                tvUserMyjifen.setText(userInfoBean.Jifen);
                tvUserMyxuebi.setText(userInfoBean.Money);
                tvUserMyxueshi.setText(userInfoBean.xueshi + "");
            }
        });
    }


    class MyUserListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return userlist_icon.length;
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
        public View getView(int i, View contentView, ViewGroup viewGroup) {
            view = View.inflate(mContext, R.layout.item_usercenter, null);
            ImageView iv_userlist_icon = view.findViewById(R.id.iv_userlist_icon);
            TextView tv_userlist_title = view.findViewById(R.id.tv_userlist_title);

            if (i == 0 || i == 5 || i == 9 || i == 12) {
                view = View.inflate(mContext, R.layout.item_line, null);
            }
            iv_userlist_icon.setImageResource(userlist_icon[i]);
            tv_userlist_title.setText(userlist_title[i]);

            return view;
        }
    }


    private void initEvent() {
        ivUserLogin.setOnClickListener(this);
        ivUserUsermessage.setOnClickListener(this);
        llUserJifen.setOnClickListener(this);
        llUserXuebi.setOnClickListener(this);
        llUserXueshi.setOnClickListener(this);
        llUserMessage.setOnClickListener(this);
        llUserShare.setOnClickListener(this);
        llUserAccount.setOnClickListener(this);
        btnUserBack.setOnClickListener(this);
        rlBackground.setOnClickListener(this);
        lvUser.setOnItemClickListener(new LvitemOnItemClickListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_login:
                // 跳转到个人信息页面
                userlogin_intent = new Intent(mContext, UserLoginMessageActivity.class);
                startActivity(userlogin_intent);
                break;
            case R.id.iv_user_usermessage:
                // 跳转到个人信息页面
                userlogin_intent = new Intent(mContext, UserLoginMessageActivity.class);
                startActivity(userlogin_intent);
                break;
            case R.id.ll_user_jifen:
                // 跳转到积分收支记录页面
                Intent userJifen_intent = new Intent(mContext, UserJifenActivity.class);
                startActivity(userJifen_intent);
                break;
            case R.id.ll_user_xuebi:
                // 跳转到学币收支记录页面
                Intent userXuebi_intent = new Intent(mContext, UserXuebiActivity.class);
                startActivity(userXuebi_intent);
                break;
            case R.id.ll_user_xueshi:
                // 跳转到学时记录页面
                Intent userXueshi_intent = new Intent(mContext, UserXueshiActivity.class);
                startActivity(userXueshi_intent);
                break;
            case R.id.ll_user_message:
                // 跳转到消息中心页面
                Intent userMessage_intent = new Intent(mContext, UserMessageActivity.class);
                startActivity(userMessage_intent);
                break;
            case R.id.ll_user_share:
                // 跳转到推荐有礼页面
                Intent userShare_intent = new Intent(mContext, UserShareActivity.class);
                startActivity(userShare_intent);
                break;
            case R.id.ll_user_account:
                // 跳转到账户中心页面
                Intent userAccount_intent = new Intent(mContext, UserAccountActivity.class);
                startActivity(userAccount_intent);
                break;
            case R.id.btn_user_back:
                /*让已经登录失效*/
                SpUtils.putBoolean(mContext, Constant.IS_LOGIN, false);
                // 点击退出跳转到登录页面
                Intent login_intent = new Intent(mContext, LoginAcitvity.class);
                startActivity(login_intent);
                break;
            case R.id.rl_background:
                // 切换背景图片
                break;

        }
    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private class LvitemOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch (position) {
                case 1:
                    Intent intent1 = new Intent(mContext, MyCollect.class);
                    startActivity(intent1);
                    break;
                case 2:
                    Intent intent2 = new Intent(mContext, MyProblem.class);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(mContext, MyNotes.class);
                    startActivity(intent3);
                    break;
                case 4:
                    Intent intent4 = new Intent(mContext, CursorZhengDing.class);
                    startActivity(intent4);
                    break;
                case 6:
                    Intent intent6 = new Intent(mContext, MyJiFen.class);
                    startActivity(intent6);
                    break;
                case 7:
                    Intent intent7 = new Intent(mContext, MyTest.class);
                    startActivity(intent7);
                    break;
                case 8:
                    Intent intent8 = new Intent(mContext, ZuZhiAndSchool.class);
                    startActivity(intent8);
                    break;
                case 10:
                    Intent intent10 = new Intent(mContext, Help.class);
                    startActivity(intent10);
                    break;
                case 11:
                    Intent intent11 = new Intent(mContext, Setting.class);
                    startActivity(intent11);
                    break;
                case 13:
                    Intent intent13 = new Intent(mContext, AboutZX.class);
                    startActivity(intent13);
                    break;

            }
        }
    }
}
