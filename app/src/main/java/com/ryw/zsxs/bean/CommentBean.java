/*
 * Create on 2017-7-2 上午8:31
 * FileName: CommentBean.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Mr_Shadow on 2017/7/2.
 * 评论
 */

public class CommentBean {

    /**
     * page_all : 1
     * page_now : 1
     * pllist : [{"id":"1484","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"哈哈","zan":"0","zan_flag":"0"},{"id":"1483","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"77777","zan":"0","zan_flag":"0"},{"id":"1482","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"啊啊啊","zan":"0","zan_flag":"0"},{"id":"1481","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"2121212","zan":"0","zan_flag":"0"},{"id":"1480","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"222","zan":"0","zan_flag":"0"},{"id":"1479","userimg":"http://www.chinaplat.com/user/UserHeadImg/164151.jpg","nickname":"996696","pl_time":"10小时前","pc_content":"2332","zan":"0","zan_flag":"0"}]
     */

    private int page_all;
    private int page_now;
    private List<PllistBean> pllist;

    public int getPage_all() {
        return page_all;
    }

    public void setPage_all(int page_all) {
        this.page_all = page_all;
    }

    public int getPage_now() {
        return page_now;
    }

    public void setPage_now(int page_now) {
        this.page_now = page_now;
    }

    public List<PllistBean> getPllist() {
        return pllist;
    }

    public void setPllist(List<PllistBean> pllist) {
        this.pllist = pllist;
    }

    public static class PllistBean {
        /**
         * id : 1484
         * userimg : http://www.chinaplat.com/user/UserHeadImg/164151.jpg
         * nickname : 996696
         * pl_time : 10小时前
         * pc_content : 哈哈
         * zan : 0
         * zan_flag : 0
         */

        private String id;
        private String userimg;
        private String nickname;
        private String pl_time;
        private String pc_content;
        private String zan;
        private String zan_flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPl_time() {
            return pl_time;
        }

        public void setPl_time(String pl_time) {
            this.pl_time = pl_time;
        }

        public String getPc_content() {
            return pc_content;
        }

        public void setPc_content(String pc_content) {
            this.pc_content = pc_content;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getZan_flag() {
            return zan_flag;
        }

        public void setZan_flag(String zan_flag) {
            this.zan_flag = zan_flag;
        }
    }
}
