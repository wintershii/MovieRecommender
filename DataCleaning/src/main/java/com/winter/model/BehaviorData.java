package com.winter.model;

public class BehaviorData {

    private String uid;

    private String itemId;

    private Integer click;

    private Integer score;

    private Integer collect;

    private Integer interval;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "BehaviorData{" +
                "uid='" + uid + '\'' +
                ", itemId='" + itemId + '\'' +
                ", click=" + click +
                ", score=" + score +
                ", collect=" + collect +
                ", interval=" + interval +
                '}';
    }
}
