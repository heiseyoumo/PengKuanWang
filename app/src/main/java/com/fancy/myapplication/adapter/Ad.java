package com.fancy.myapplication.adapter;

import java.io.Serializable;

/**
 * Created by YangJk on 2017/6/14.
 */

public class Ad implements Serializable {

    private String id;//广告ID
    private String adName;//广告名称
    private String adContent;//广告的图片地址
    private String adUrl;//链接地址
    private String adDesc;//广告说明
    private int sort;//显示顺序
    private String positionName;//广告位置名称
    private String type;
    private String adWord;

    public String getType() {
        return type;
    }

    public Ad(String type, String adDesc, String adName) {
        this.type = type;
        this.adDesc = adDesc;
        this.adName = adName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdDesc() {
        return adDesc;
    }

    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getAdWord() {
        return adWord;
    }

    public void setAdWord(String adWord) {
        this.adWord = adWord;
    }
}
