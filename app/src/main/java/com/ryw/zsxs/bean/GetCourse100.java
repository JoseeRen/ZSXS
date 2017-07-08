package com.ryw.zsxs.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shishaoyou on 2017/7/1.
 */

public class GetCourse100 implements Serializable {

    private List<CourseBean> Course;

    public List<CourseBean> getCourse() {
        return Course;
    }

    public void setCourse(List<CourseBean> Course) {
        this.Course = Course;
    }

    public static class CourseBean implements Serializable{
        /**
         * kc_id : 140478
         * title : 响应式网页设计
         * img : http://www.chinaplat.com/CourseImg/IMG-20161224/20161224095173197319.png
         * info :
         * kc_money : 100
         */

        private String kc_id;
        private String title;
        private String img;
        private String info;
        private int kc_money;

        public String getKc_id() {
            return kc_id;
        }

        public void setKc_id(String kc_id) {
            this.kc_id = kc_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getKc_money() {
            return kc_money;
        }

        public void setKc_money(int kc_money) {
            this.kc_money = kc_money;
        }
    }
}
