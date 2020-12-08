package com.winter.model;

public class CollectData {

    private Integer uid;

    private Integer mid;

    private String date;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CollectData{" +
                "uid=" + uid +
                ", mid=" + mid +
                ", date='" + date + '\'' +
                '}';
    }
}
