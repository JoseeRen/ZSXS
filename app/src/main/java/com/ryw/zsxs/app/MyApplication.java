/*
 * Create on 2017-6-8 下午4:36
 * FileName: MyApplication.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.app;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public MyApplication() {
        myApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {

    }
}
