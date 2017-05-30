package com.chssl17.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class BannerEntity extends BmobObject {
    public String pic;
    public String picUrl;
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BannerEntity{" +
                "pic='" + pic + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
