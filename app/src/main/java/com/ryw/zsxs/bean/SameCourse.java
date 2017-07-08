/*
 * Create on 2017-7-1 下午9:23
 * FileName: SameCourse.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Mr_Shadow on 2017/7/1.
 * 同类推荐bean类
 */

public class SameCourse {

    /**
     * kc_types : 0
     * sameCourse : [{"kc_id":"120885","title":"一级人力资源管理师（二）：招聘与配置","img":"http://www.chinaplat.com/CourseImg/IMG-20170413/20170413171493069306.jpg","info":"人力资源管理一级（高级）考试第二章招聘与配置内容，课程由资深人力资源老师讲解，是以课本为章节顺序的精...","money":"5000","hot":311,"keshi":17,"teacher":"华图"},{"kc_id":"109193","title":"企业人力资源管理师一级真题解析","img":"http://www.chinaplat.com/CourseImg/IMG-20150412/20150412222097669766.jpg","info":"人力资源一级专业技能真题解析视频教程针对考试教材，以专业教学团队为主导，全面洞悉编委的出发点，在各章...","money":"1000","hot":443,"keshi":19,"teacher":"王少博"},{"kc_id":"109057","title":"2015年 企业人力资源管理师（二级）","img":"http://www.chinaplat.com/CourseImg/IMG-20150404/2015040421540160160.jpg","info":"2015年5月 企业人力资源管理师（二级）培训视频教程，由知名教师为大家讲授企业人力资源管理的六个方...","money":"625","hot":453,"keshi":25,"teacher":"思"},{"kc_id":"120888","title":"一级人力资源管理师（三）：培训与开发","img":"http://www.chinaplat.com/CourseImg/IMG-20170413/20170413171459645964.jpg","info":"人力资源管理一级（高级）考试第三章培训与开发内容，课程由资深人力资源老师讲解，是以课本为章节顺序的精...","money":"5000","hot":229,"keshi":15,"teacher":"华图"},{"kc_id":"108624","title":"人力资源管理师三级（2015新版教材）","img":"http://www.chinaplat.com/CourseImg/IMG-20150312/20150312131885278527.jpg","info":"2015最新版人力资源管理师三级考试教材，针对考试大纲，模块化教学，重点突出，点题准确。由南开大学李...","money":"20000","hot":1561,"keshi":31,"teacher":"追梦蝶"},{"kc_id":"118256","title":"人力资源管理师二级实操课程","img":"http://www.chinaplat.com/CourseImg/IMG-20150804/20150804152196249624.jpg","info":"为了掌握人力资源管理师的内容，提高实际能力及应用，1.此讲座严格按照人力资源管理师培训教程顺序进行；...","money":"1000","hot":606,"keshi":3,"teacher":"金色未来"}]
     */

    private String kc_types;
    private List<CourseBean> sameCourse;

    public String getKc_types() {
        return kc_types;
    }

    public void setKc_types(String kc_types) {
        this.kc_types = kc_types;
    }

    public List<CourseBean> getSameCourse() {
        return sameCourse;
    }

    public void setSameCourse(List<CourseBean> sameCourse) {
        this.sameCourse = sameCourse;
    }


}
