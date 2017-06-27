package com.ryw.zsxs.bean;

import java.util.List;

/**
 * Created by shishaoyou on 2017/6/24.
 * 首页轮播图
 */

public class SlideBean {

    private List<SlidesBean> Slides;

    public List<SlidesBean> getSlides() {
        return Slides;
    }

    public void setSlides(List<SlidesBean> Slides) {
        this.Slides = Slides;
    }

    public  class SlidesBean {
        /**
         * pic : http://www.chinaplat.com/imgzt/IMG-20170124/20170124140783108310.png
         * title : 鍚嶆牎璇剧▼
         * picURL : 112
         * pictype : app
         */

        private String pic;
        private String title;
        private String picURL;
        private String pictype;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }

        public String getPictype() {
            return pictype;
        }

        public void setPictype(String pictype) {
            this.pictype = pictype;
        }
    }
}
