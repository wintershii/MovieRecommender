package com.winter.domain;

import javax.persistence.*;

@Entity
@Table(name = "movie_collect")
public class MovieCollect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "u_id")
    private Integer uid;

    @Column(name = "m_id")
    private Integer mid;

    @Column(name = "deleted")
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "MovieCollect{" +
                "id=" + id +
                ", uid=" + uid +
                ", mid=" + mid +
                ", deleted=" + deleted +
                '}';
    }
}
