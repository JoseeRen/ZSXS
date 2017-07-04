package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Y.Q on 2017/6/25.
 */

public class XueBiBean {


    /**
     * page_all : 1
     * page_now : 1
     * list : [{"addtime":"2017-6-11 10:41:54","shouzhi":"+ ","money":588,"title":"账号注册赠送积分"}]
     */

    public int page_all;
    public int page_now;
    public List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * addtime : 2017-6-11 10:41:54
         * shouzhi : +
         * money : 588
         * title : 账号注册赠送积分
         */

        private String addtime;
        private String shouzhi;
        private int money;
        private String title;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getShouzhi() {
            return shouzhi;
        }

        public void setShouzhi(String shouzhi) {
            this.shouzhi = shouzhi;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

