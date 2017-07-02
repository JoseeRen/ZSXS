/*
 * Create on 2017-7-2 下午12:05
 * FileName: BookCourseBean.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

/**
 * Created by Mr_Shadow on 2017/7/2.
 * 读书的bean类
 */

public class BookCourseBean {

    /**
     * shoucang : 0
     * kc_img : http://www.chinaplat.com/CourseImg/IMG-20150404/20150404102515211521.jpg
     * buy : 0
     * kc_id : 108939
     * kc_title : PS CS5新手教程
     * kc_info : 一本非常详细的图文教程，对于新手绝对是个宝藏哦！还等什么呢？！
     * kc_money : 100
     * kc_content : ii
     * teacher : 孩心
     * hot : 542
     * teacher_id : 10037
     */

    private String shoucang;
    private String kc_img;
    private String buy;
    private String kc_id;
    private String kc_title;
    private String kc_info;
    private String kc_money;
    private String kc_content;
    private String teacher;
    private int hot;
    private int teacher_id;

    public String getShoucang() {
        return shoucang;
    }

    public void setShoucang(String shoucang) {
        this.shoucang = shoucang;
    }

    public String getKc_img() {
        return kc_img;
    }

    public void setKc_img(String kc_img) {
        this.kc_img = kc_img;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
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

    public String getKc_content() {
        return kc_content;
    }

    public void setKc_content(String kc_content) {
        this.kc_content = kc_content;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
