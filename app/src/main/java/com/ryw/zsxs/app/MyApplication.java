/*
 * Create on 2017-6-8 下午4:36
 * FileName: MyApplication.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public MyApplication() {
        myApplication = this;
    }
    private DbManager.DaoConfig daoConfig;
    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
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
        Log.e("Application", "setupDatabase: " );
        daoConfig=new DbManager.DaoConfig()
                .setDbName("db")
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
    }


}
