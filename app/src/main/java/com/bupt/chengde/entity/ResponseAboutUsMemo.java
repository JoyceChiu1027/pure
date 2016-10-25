package com.bupt.chengde.entity;

/**
 * Created by wyf on 2015/12/15.
 *
 * @author wyf
 * @version 1.0
 * @类 :关于我们
 */
public class ResponseAboutUsMemo {

    private String memo;
    private String picurl;
    private String userFeedbackQq;
    private String publicWeixin;
    private String companyCopyright;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserFeedbackQq() {
        return userFeedbackQq;
    }

    public void setUserFeedbackQq(String userFeedbackQq) {
        this.userFeedbackQq = userFeedbackQq;
    }

    public String getPublicWeixin() {
        return publicWeixin;
    }

    public void setPublicWeixin(String publicWeixin) {
        this.publicWeixin = publicWeixin;
    }

    public String getCompanyCopyright() {
        return companyCopyright;
    }

    public void setCompanyCopyright(String companyCopyright) {
        this.companyCopyright = companyCopyright;
    }

    @Override
    public String toString() {
        return "ResponseAboutUsMemo{" +
                "memo='" + memo + '\'' +
                ", picurl='" + picurl + '\'' +
                ", userFeedbackQq='" + userFeedbackQq + '\'' +
                ", publicWeixin='" + publicWeixin + '\'' +
                ", companyCopyright='" + companyCopyright + '\'' +
                '}';
    }
}
