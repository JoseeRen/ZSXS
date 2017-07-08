package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Y.Q on 2017/7/1.
 * 消息中心bean
 */

public class MessageCenterBean {

    public List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 0
         * addtime : 2016-01-01
         * read : 1
         * title : 推荐好友下载APP送大礼
         * content : 推荐20好友注册并下载APP立送小巨蛋牙膏一个，进入个人中心--推荐好礼栏目获取自己的推荐码或推荐连接。
         */

        private String id;
        private String addtime;
        private String read;
        private String title;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
