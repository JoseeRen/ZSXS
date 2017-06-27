package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by Mr_Shadow on 2017/6/9.
 */
public class KCTypes  {

    /**
     * kc_types : 0
     * t_list : [{"id":"4","name":"考试"},{"id":"5","name":"工作"},{"id":"10","name":"生活"}]
     */

    private String kc_types;
    private List<TListBean> t_list;





    public String getKc_types() {
        return kc_types;
    }

    public void setKc_types(String kc_types) {
        this.kc_types = kc_types;
    }

    public List<TListBean> getT_list() {
        return t_list;
    }

    public void setT_list(List<TListBean> t_list) {
        this.t_list = t_list;
    }



    public  class TListBean {
        /**
         * id : 4
         * name : 考试
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
