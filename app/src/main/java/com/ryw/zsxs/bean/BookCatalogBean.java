/*
 * Create on 2017-7-2 下午4:17
 * FileName: BookCatalogBean.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Mr_Shadow on 2017/7/2.
 * 图书目录bean类
 */

public class BookCatalogBean {

    /**
     * kc_id : 108939
     * kc_name : PS CS5新手教程
     * index_1 : [{"title_1":"第一篇","zhang_id":2248,"index_2":[{"title_2":"对PhotoShop的基础界面操作讲解","b_id":"4809"},{"title_2":"文件的新建、打开和保存","b_id":"4810"},{"title_2":"PhotoShop选择、移动、索套工具的使用","b_id":"4811"}]},{"title_1":"第二篇","zhang_id":2249,"index_2":[{"title_2":"关于选区的一点知识","b_id":"4812"},{"title_2":"学习裁剪工具、污点修复画笔工具","b_id":"4813"},{"title_2":"仿制图章工具和图案图 章工具","b_id":"4814"}]},{"title_1":"第三篇","zhang_id":2250,"index_2":[{"title_2":"橡皮擦、锐化工具、模糊、锐化、涂抹工具","b_id":"4815"},{"title_2":"减淡、加深、海绵工具的使用","b_id":"4816"},{"title_2":"画笔、铅笔、颜色替换工具","b_id":"4817"},{"title_2":"渐变工具、油漆桶","b_id":"4818"}]},{"title_1":"第四篇","zhang_id":2251,"index_2":[{"title_2":"学习钢笔工具、自由钢笔工具、锚点工具","b_id":"4819"},{"title_2":"路径工具、文本工具、自定义形状工具","b_id":"4820"},{"title_2":"辅助工具、吸管工具、测量工具、前景色背景色","b_id":"4821"},{"title_2":"图层一","b_id":"4822"},{"title_2":"图层二","b_id":"4823"}]},{"title_1":"第五篇","zhang_id":2252,"index_2":[{"title_2":"选区的使用","b_id":"4824"},{"title_2":"自由变换和变换","b_id":"4825"}]}]
     */

    private String kc_id;
    private String kc_name;
    private List<Index1Bean> index_1;

    public String getKc_id() {
        return kc_id;
    }

    public void setKc_id(String kc_id) {
        this.kc_id = kc_id;
    }

    public String getKc_name() {
        return kc_name;
    }

    public void setKc_name(String kc_name) {
        this.kc_name = kc_name;
    }

    public List<Index1Bean> getIndex_1() {
        return index_1;
    }

    public void setIndex_1(List<Index1Bean> index_1) {
        this.index_1 = index_1;
    }

    public static class Index1Bean {
        /**
         * title_1 : 第一篇
         * zhang_id : 2248
         * index_2 : [{"title_2":"对PhotoShop的基础界面操作讲解","b_id":"4809"},{"title_2":"文件的新建、打开和保存","b_id":"4810"},{"title_2":"PhotoShop选择、移动、索套工具的使用","b_id":"4811"}]
         */

        private String title_1;
        private int zhang_id;
        private List<Index2Bean> index_2;

        public String getTitle_1() {
            return title_1;
        }

        public void setTitle_1(String title_1) {
            this.title_1 = title_1;
        }

        public int getZhang_id() {
            return zhang_id;
        }

        public void setZhang_id(int zhang_id) {
            this.zhang_id = zhang_id;
        }

        public List<Index2Bean> getIndex_2() {
            return index_2;
        }

        public void setIndex_2(List<Index2Bean> index_2) {
            this.index_2 = index_2;
        }

        public static class Index2Bean {
            /**
             * title_2 : 对PhotoShop的基础界面操作讲解
             * b_id : 4809
             */

            private String title_2;
            private String b_id;

            public String getTitle_2() {
                return title_2;
            }

            public void setTitle_2(String title_2) {
                this.title_2 = title_2;
            }

            public String getB_id() {
                return b_id;
            }

            public void setB_id(String b_id) {
                this.b_id = b_id;
            }
        }
    }
}
