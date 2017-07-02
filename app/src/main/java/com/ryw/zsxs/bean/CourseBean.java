/*
 * Create on 2017-7-2 上午10:58
 * FileName: CourseBean.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

import java.io.Serializable;

/**
 * Created by Mr_Shadow on 2017/7/2.
 */

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

    private String kc_id;
    private String title;

    private String img;

    private String info;

    private String money;


    private int keshi;

    private int hot;

    private String teacher;


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

    @Override
    public String toString() {
        return "CourseBean{" +
                "kc_id='" + kc_id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", info='" + info + '\'' +
                ", money='" + money + '\'' +
                ", keshi=" + keshi +
                ", hot=" + hot +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
