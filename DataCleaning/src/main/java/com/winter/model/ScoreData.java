package com.winter.model;

public class ScoreData {

    private Integer uid;

    private Integer mid;

    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "uid=" + uid +
                ", mid=" + mid +
                ", score=" + score +
                ", date='" + date + '\'' +
                '}';
    }
}
