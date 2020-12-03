package com.winter.domain;

import java.util.List;

public class MovieVo {
    private Integer id;

    private String name;

    private String score;

    private String peoples;

    private String describe;

    private String picUrl;

    private String director;

    private String actor;

    private String plot;

    private List<String> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPeoples() {
        return peoples;
    }

    public void setPeoples(String peoples) {
        this.peoples = peoples;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "MovieVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", peoples='" + peoples + '\'' +
                ", describe='" + describe + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", plot='" + plot + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
