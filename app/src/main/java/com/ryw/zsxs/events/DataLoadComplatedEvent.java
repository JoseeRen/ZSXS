/*
 * Create on 2017-6-28 下午6:58
 * FileName: DataLoadComplatedEvent.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.events;

/**
 * Created by Mr_Shadow on 2017/6/28.
 * 数据加载完成  可以显示
 */

public class DataLoadComplatedEvent {
    public final String message;

    public DataLoadComplatedEvent(String message) {
        this.message = message;
    }
}
