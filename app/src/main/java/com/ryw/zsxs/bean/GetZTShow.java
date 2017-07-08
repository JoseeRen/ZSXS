package com.ryw.zsxs.bean;

import java.io.Serializable;
import java.util.List;



/**
 * Created by shishaoyou on 2017/6/28.
 */

public class GetZTShow implements Serializable {
    private String content;
    private String imgURL;
    private int page_all;
    private int page_now;
    private String ZTtitle;
    private List<CourseBean> Course;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

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

    public String getZTtitle() {
        return ZTtitle;
    }

    public void setZTtitle(String ZTtitle) {
        this.ZTtitle = ZTtitle;
    }

    public List<CourseBean> getCourse() {
        return Course;
    }

    public void setCourse(List<CourseBean> Course) {
        this.Course = Course;
    }



}
