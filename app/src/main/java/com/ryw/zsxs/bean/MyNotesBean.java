package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Zhao on 2017/6/25.
 */

public class MyNotesBean {

    /**
     * kc_types : 0
     * courselist : [{"kc_id":"121458","title":"一级人力资源管理师\u2014笑过公文筐","img":"http://www.chinaplat.com/CourseImg/IMG-20160503/217_156_20160503104113631363.jpg","info":"一级人力资源管理师\u2014笑过公文筐","notescounts":1}]
     */

    public int kc_types;
    public List<CourselistBean> courselist;

    

    public static class CourselistBean {
        /**
         * kc_id : 121458
         * title : 一级人力资源管理师—笑过公文筐
         * img : http://www.chinaplat.com/CourseImg/IMG-20160503/217_156_20160503104113631363.jpg
         * info : 一级人力资源管理师—笑过公文筐
         * notescounts : 1
         */

        public String kc_id;
        public String title;
        public String img;
        public String info;
        public int notescounts;

        
      
    }
}
