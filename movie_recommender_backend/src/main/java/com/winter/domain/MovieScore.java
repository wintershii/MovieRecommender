package com.winter.domain;

import javax.persistence.*;

@Entity
@Table(name = "movie_scores")
public class MovieScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "u_id")
    private Integer uId;

    @Column(name = "m_id")
    private Integer MId;

    @Column(name = "score")
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getmId() {
        return MId;
    }

    public void setmId(Integer mId) {
        this.MId = mId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
