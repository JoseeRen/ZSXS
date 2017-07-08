/*
 * Create on 2017-7-2 下午6:32
 * FileName: BookContent.java
 * Author: Ren Yaowei
 * Blog: http://www.renyaowei.top
 * Email renyaowei@foxmail.com
 */

package com.ryw.zsxs.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Mr_Shadow on 2017/7/2.
 */
@Table(name = "bookContent")
public class BookContent implements Serializable{

    /**
     * kc_id : 108939
     * kc_title : PS CS5新手教程
     * title_2 : 对PhotoShop的基础界面操作讲解
     * content : 请大家安装好PS（这不是废话嘛……），然后将PS的界面熟悉一下，消除对PS的惧怕心理~~安装完毕后，打开PS，就进入了PS的操作界面，我们来看一下【图1.1】。
     * 有点看晕了？呵呵，不要紧，接下来我们会详细讲解。最上面是菜单栏，文件，编辑，图像，等等。这里是一些基本操作选项，我们在实际的操作中会使用到，现在先不多说。菜单栏的下面是属性栏，当我们选择了工具栏中的工具时，这里就显示该工具的相关属性。我们在讲解工具的时候会详细说。最中间的灰色区域是文件编辑区。文件都在这里进行编辑。左边是工具栏，这里列出了PS的基本工具。我们可以通过菜单栏上的“窗口”－“工具”按钮，来显示和隐藏工具栏。
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * 【图1.2】
     * <p>
     * 将鼠标放在某个工具上，可以显示这个工具的名称。有些工具的右下角还有一个三角，就表示这个一个组合工具，这里还有其他的工具。用鼠标长按这个三角，就会把这里包含的工具都显示出来。
     * 【图1.3】
     * 右边是一些活动面板，包括图层、历史记录等等。这里的面板都可以最小化或者关闭。按一下菜单栏上的“窗口”，这里所列出的就是活动面板的内容。打勾的表示这个面板当前是显示的，没有打勾的就是不显示，你可以通过这里选择你想显示和隐藏的面板。如果什么面板找不到了，都可以去“窗口”这里找，在这里点一下，就打开了。
     * 【图1.4】这些活动面板都可以随意拖动，你可以将他们拖到屏幕上任何的位置。这样做的话，时间久了，可能屏幕上会变得很乱，我们可以通过一个操作迅速将面板排列到PS的默认设置。按一下“窗口”－“工作区”－“默认工作区”，所有的面板都排列整齐了，恢复了默认的设置。
     * 按住键盘上的TAB键，可以将所有的工具栏和面板隐藏，同时按住SHIFT和TAB键，可以隐藏右边的活动面板。当你作图时，因为面板的遮挡无法看清图片的全貌，这个操作就非常有用。在众多的面板里，我们先来讲一下“导航器”面板。我们在作图的过程中，经常需要缩放图片的显示大小，移动图片的位置，导航器就实现这些功能。
     * 【图1.6】
     * <p>
     * <p>
     * proid :
     * nextid : 4810
     * zhang_id : 2248
     */
    @Column(name = "kc_id")
    private String kc_id;

    @Column(name = "kc_title")
    private String kc_title;

    @Column(name = "title_2")
    private String title_2;

    @Column(name = "content")
    private String content;

    @Column(name = "proid")
    private String proid;

    @Column(name = "nextid")
    private String nextid;

    @Column(name = "zhang_id", isId = true,autoGen = false)
    private String zhang_id;

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

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getNextid() {
        return nextid;
    }

    public void setNextid(String nextid) {
        this.nextid = nextid;
    }

    public String getZhang_id() {
        return zhang_id;
    }

    public void setZhang_id(String zhang_id) {
        this.zhang_id = zhang_id;
    }
}
