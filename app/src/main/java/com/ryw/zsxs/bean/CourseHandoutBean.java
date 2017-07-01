/*
 * Create on 2017-7-1 下午5:40
 * FileName: CourseHandoutBean.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;


import java.util.List;

/**
 * Created by Mr_Shadow on 2017/7/1.
 * 课程讲义bean类
 */

public class CourseHandoutBean {

    /**
     * kc_id : 140703
     * list : [{"title":"企业人力资源管理师教材（二级）","URL":""},{"title":"企业人力资源管理师教材（基础知识）","URL":""}]
     *
     * 讲义下载地址，为空表示用户没有权限下载
     */

    private int kc_id;
    private List<ListBean> list;

    public int getKc_id() {
        return kc_id;
    }

    public void setKc_id(int kc_id) {
        this.kc_id = kc_id;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 企业人力资源管理师教材（二级）
         * URL :
         */

        private String title;
        private String URL;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }
}

