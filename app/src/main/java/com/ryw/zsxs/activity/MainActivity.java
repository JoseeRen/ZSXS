/*
 * Create on 2017-6-8 下午2:18
 * FileName: MainActivity.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.fragment.Home_Fragment;
import com.ryw.zsxs.fragment.MyClass_Fragment;
import com.ryw.zsxs.fragment.User_Fragment;
import com.ryw.zsxs.fragment.XuanKe_Fragment;
import com.ryw.zsxs.utils.SpUtils;

import butterknife.BindView;

/**
 * 任耀威
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @BindView(R.id.foot_bar_home)
    RadioButton rb_footBarHome;
    @BindView(R.id.foot_bar_xuanke)
    RadioButton rb_footBarXuanke;
    @BindView(R.id.foot_bar_myclass)
    RadioButton rb_footBarMyclass;
    @BindView(R.id.foot_bar_user)
    RadioButton rb_footBarUser;
    @BindView(R.id.group)
    RadioGroup rg_group;
    @BindView(R.id.layoutFooter)
    RelativeLayout layoutFooter;
    int mCurrent=0;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        rg_group.check(R.id.foot_bar_home);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_main,Home_Fragment.getInstance());
        ft.commit();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        rg_group.setOnCheckedChangeListener(new RadioGroupCheckedChangeListener());
    }



    private class RadioGroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        private boolean isLogin;

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (checkedId) {
                case R.id.foot_bar_home:
                    ft.replace(R.id.rl_main, Home_Fragment.getInstance());
                    mCurrent=0;
                    break;
                case R.id.foot_bar_xuanke:
                    ft.replace(R.id.rl_main, XuanKe_Fragment.getInstance());
                    mCurrent=0;

                    break;
                case R.id.foot_bar_myclass:
                    isLogin = SpUtils.getBoolean(mContext, Constant.IS_LOGIN);
                    if (!isLogin){
                        mCurrent=1;
                        startActivity(LoginAcitvity.class,null);


                        return;
                    }
                    ft.replace(R.id.rl_main, MyClass_Fragment.getInstance());


                    break;
                case R.id.foot_bar_user:
                    isLogin = SpUtils.getBoolean(mContext, Constant.IS_LOGIN);

                    if (!isLogin){
                        startActivity(LoginAcitvity.class,null);
                        mCurrent=1;



                        return;
                    }
                    ft.replace(R.id.rl_main, User_Fragment.getInstance());


                    break;
            }
            ft.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrent!=0)
        rb_footBarHome.setChecked(true);
        mCurrent=0;


    }
}
