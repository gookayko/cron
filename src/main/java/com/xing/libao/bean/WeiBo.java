package com.xing.libao.bean;

/**
 * Created with IntelliJ IDEA.
 * User: xingzhe
 * Date: 2015/7/3
 * Time: 16:49
 */
public class WeiBo {
    private int id;
    private long uid;
    private long mid;
    private String text;
    private String name;
    private int verifiedType;

    public WeiBo(){

    }

    public WeiBo(long uid, long mid, String text, String name, int verifiedType) {
        this.uid = uid;
        this.mid = mid;
        this.text = text;
        this.name = name;
        this.verifiedType = verifiedType;
    }

    @Override
    public String toString() {
        return "WeiBo{" +
                "id=" + id +
                ", uid=" + uid +
                ", mid=" + mid +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", verifiedType=" + verifiedType +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(int verifiedType) {
        this.verifiedType = verifiedType;
    }
}
