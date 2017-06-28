package com.ryw.zsxs.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by gui on 2017/6/28.
 */
@Table(name = "kc_info")
public class Kc_Course {
      @Column(name="id",isId=true,autoGen=true)
      public int id;
     @Column(name="flag")
     public int flag;  //类型  0 1 2 3

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Column(name="kc_id")
      public String kc_id;
      @Column(name="kc_title")
      public String kc_title;
      @Column(name="kc_info")
      public String kc_info;
      @Column(name = "kc_money")
      public String kc_money;
      @Column(name="hot")
      public String hot;
    public Kc_Course(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKc_id() {
        return kc_id;
    }

    public void setKc_id(String kc_id) {
        this.kc_id = kc_id;
    }

    public String getKc_title() {
        return kc_title;
    }

    public void setKc_title(String kc_title) {
        this.kc_title = kc_title;
    }

    public String getKc_info() {
        return kc_info;
    }

    public void setKc_info(String kc_info) {
        this.kc_info = kc_info;
    }

    public String getKc_money() {
        return kc_money;
    }

    public void setKc_money(String kc_money) {
        this.kc_money = kc_money;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public class CourseBean implements Serializable {
        /**
         * kc_id : 140703
         * title : 人力资源管理师二级（基础+专业知识）
         * img : http://www.chinaplat.com/CourseImg/IMG-20170414/2017041410490314314.jpg
         * info : 人力资源管理师二级考试教材视频教程，针对考试大纲，模块化教学，重点突出，点题准确。课程包含：人力资源...
         * money : 12900
         * keshi : 54
         * hot : 192
         * teacher : ZS_34147
         */
        public String kc_id;
        public String title;

        public String img;

        public String info;

        public String money;


        public int keshi;

        public int hot;

        public String teacher;


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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getKeshi() {
            return keshi;
        }

        public void setKeshi(int keshi) {
            this.keshi = keshi;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }
    }
}
