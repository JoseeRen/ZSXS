/*
 * Create on 2017-6-8 下午4:35
 * FileName: Constant.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.app;

/**
 * Created by Mr_Shadow on 2017/6/8.
 * 常量存放
 */

public class Constant {
    public static final String SAVE_AD_PATH = MyApplication.getInstance().getFilesDir().getPath() + "/app_hy1.jpg";
    public static final String IS_LOGIN = "is_Login";
    //已购课程
    public static final String BUYEDCLASS = "BUYEDCLASS";
    //历史记录
    public static final String HISTORYRECORD = "HISTORYRECORD";

    //config   应用的一些配置


    //url   请求的url
    //接口根路径
    public static String HOSTNAME = "http://api.chinaplat.com/getval_2017?";
    //splash页面的adurl
    public static String ACTION_GETADPIC = "Action=getADpic";
    //获取课程分类列表
    public static String ACTION_COURSETYPES = "Action=GetCourseTypesList";
    //	获取课程列表信息GetCourseList
    public static String ACTION_GETCOURSELIST = "GetCourseList";


    private Constant() {

    }

    public static String ACONDE = "02c1c3dacbbc74b67f5190df5bbcc735";
    public static String PWD = "938CA204B9830D430F419C1553DB00C4";
    public static String MYCODE = "1164151";
    public static String USERNAME = "18733502093";


}
