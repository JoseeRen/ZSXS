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
      private int id;
     @Column(name="flag")
     private String  flag;  //类型  0 1 2 3
     @Column(name="kc_keshi")
     private String keshi;
    @Column(name="kc_id")
    private String kc_id;
    @Column(name="kc_title")
    private String kc_title;
    @Column(name="kc_info")
    private String kc_info;
    @Column(name = "kc_money")
    private String kc_money;
    @Column(name="hot")
    private String hot;
    @Column(name="isCheck")
    private  boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getFlag() {
        return flag;
    }

    public void setFlag(String  flag) {
        this.flag = flag;
    }

    public String getKeshi() {
        return keshi;
    }

    public void setKeshi(String keshi) {
        this.keshi = keshi;
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

    @Override
    public String toString() {
        return getFlag()+getKc_title()+getKc_info()+getHot()+getKc_money();
    }
}
