package com.winter.domain;

public class MoviePredict {
    private Integer mid;
    private Double predictScore;

    public MoviePredict(Integer mid, Double predictScore) {
        this.mid = mid;
        this.predictScore = predictScore;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Double getPredictScore() {
        return predictScore;
    }

    public void setPredictScore(Double predictScore) {
        this.predictScore = predictScore;
    }

    @Override
    public String toString() {
        return "MoviePredict{" +
                "mid=" + mid +
                ", predictScore=" + predictScore +
                '}';
    }
}
