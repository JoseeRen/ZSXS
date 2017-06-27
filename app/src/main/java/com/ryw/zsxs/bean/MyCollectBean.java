package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Zhao on 2017/6/24.
 */

public class MyCollectBean {

    /**
     * page_all : 1
     * page_now : 1
     * Course : [{"kc_id":"140725","title":"2017骞村娓歌\u20ac冭瘯銆愬ぇ绾插垎鏋愩\u20ac戣搴�","img":"http://www.chinaplat.com/CourseImg/IMG-20170509/217_156_20170509142596339633.png","info":"2017骞村娓歌\u20ac冭瘯銆愬ぇ绾插垎鏋愩\u20ac戣搴�","money":"2900","keshi":4,"hot":676,"teacher":"闄堥緳"}]
     */

    public String page_all;
    public String page_now;
    public List<CourseBean> Course;

    public  class CourseBean {
        /**
         * kc_id : 140725
         * title : 2017骞村娓歌€冭瘯銆愬ぇ绾插垎鏋愩€戣搴�
         * img : http://www.chinaplat.com/CourseImg/IMG-20170509/217_156_20170509142596339633.png
         * info : 2017骞村娓歌€冭瘯銆愬ぇ绾插垎鏋愩€戣搴�
         * money : 2900
         * keshi : 4
         * hot : 676
         * teacher : 闄堥緳
         */

        public String kc_id;
        public String title;
        public String img;
        public String info;
        public String money;
        public int keshi;
        public int hot;
        public String teacher;

        
        
    }
}
