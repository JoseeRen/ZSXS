/*
 * Create on 2017-6-8 下午4:12
 * FileName: Db.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.db;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by Mr_Shadow on 2017/6/8.
 */

public class Db {
    static DbManager.DaoConfig daoConfig;
    static DbManager  db;
    public static DbManager.DaoConfig getDaoConfig(){
        File file=new File(Environment.getExternalStorageDirectory().getParent()+"db");
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("kc_info.db")
                    .setDbDir(file)
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }


}
