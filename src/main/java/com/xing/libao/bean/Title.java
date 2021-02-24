package com.xing.libao.bean;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/6/8
 * Time: 16:28
 */
public class Title {
    private int id;
    private String title;
    private String url;
    private String md5;
    private int fid;
    private int time;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
