/*
 * Create on 2017-6-28 下午10:31
 * FileName: StringUtil.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mr_Shadow on 2017/5/14.
 * 字符串相关的类
 */

public class StringUtil {
    private static final int HOUR = 1000 * 60 * 60;
    private static final int MIN = 1000 * 60;
    private static final int SEC = 1000;
    private static String time;

    /**
     * 格式化时长 的方法
     */
    public static String formatDuration(int duration) {
        int hour = duration / HOUR;

        int min = duration % HOUR / MIN;
        int sec = duration % MIN / SEC;
        if (hour == 0) {

            time = String.format("%02d:%02d", min, sec);


        } else {
            time = String.format("%02d:%02d:%02d", hour, min, sec);


        }


        return time;

    }

    /**
     * 获取格式化后的系统时间
     * hh:MM:ss
     *
     * @return
     */
    public static String getSystemTime() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

        String time = sf.format(date);

        return time;
    }

    /**
     * 格式化音频名称
     */
    public static String formatAudioName(String name) {
        //name.mp3
        //substring   包含头不包含尾
        name = name.substring(0, name.lastIndexOf("."));
        return name;


    }
}
