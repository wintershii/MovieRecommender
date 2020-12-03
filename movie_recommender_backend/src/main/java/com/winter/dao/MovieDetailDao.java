package com.winter.dao;

import com.winter.domain.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDetailDao extends JpaRepository<MovieDetail, Integer> {
//    List<MovieDetail> findByMId(Integer mId);
    MovieDetail getBymId(Integer mId);
}
