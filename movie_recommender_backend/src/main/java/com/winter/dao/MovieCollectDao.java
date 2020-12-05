package com.winter.dao;

import com.winter.domain.MovieCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MovieCollectDao extends JpaRepository<MovieCollect, Integer> {
    List<MovieCollect> getMovieCollectsByUidAndDeleted(Integer uid, Integer deleted);

    boolean existsByUidAndMid(Integer uid, Integer mid);

    @Modifying
    @Transactional
    @Query(value = "update movie_collect m set m.deleted = 1 where m.u_id = :uid and m.m_id = :mid", nativeQuery = true)
    void update(@Param("uid") Integer uid, @Param("mid") Integer mid);
}
