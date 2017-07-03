/*
 * Create on 2017-7-2 下午6:37
 * FileName: BookContentDownloader.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.downloadmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ryw.zsxs.activity.LoginAcitvity;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.app.MyApplication;
import com.ryw.zsxs.bean.BookCatalogBean;
import com.ryw.zsxs.bean.BookContent;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr_Shadow on 2017/7/2.
 * 图书下载的manager
 * http://api.chinaplat.com/getval_2017?Action=getBookContent
 * &acode=02c1c3dacbbc74b67f5190df5bbcc735&uid=18733502093&Kc_id=108939&B_id=4809
 */

public class BookContentDownloader {


    static Gson gson = new Gson();

    public static void downloadBook(Context mcontext, String kc_id, List<BookCatalogBean.Index1Bean.Index2Bean> downloadList) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Action", "getBookContent");
        map.put("acode", SpUtils.getString(mcontext, LoginAcitvity.ACODE));
        map.put("uid", SpUtils.getString(mcontext, LoginAcitvity.USERNAME));
        map.put("Kc_id", kc_id);
        if (downloadList.size() == 0) {
            return;
        }
        for (BookCatalogBean.Index1Bean.Index2Bean index2Bean : downloadList) {
            map.put("B_id", index2Bean.getB_id());
            download(map);
        }
    }

    private static void download(Map map) {
        XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                BookContent bookContent = gson.fromJson(result, BookContent.class);
                DbManager.DaoConfig config = MyApplication.getInstance().getDaoConfig();
                DbManager db = x.getDb(config);
                try {
                    db.save(bookContent);
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (com.google.gson.JsonSyntaxException e) {
                    Log.e("downloadmanger", "onResponse: " + result);

                }
                try {
                    db.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
