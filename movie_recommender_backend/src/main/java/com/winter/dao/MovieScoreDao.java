package com.winter.dao;

import com.winter.domain.MovieScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MovieScoreDao extends JpaRepository<MovieScore, Integer> {

//    @Modifying
//    @Query(value = "select score from movie_scores where u_id = ?1 and m_id = ?2", nativeQuery = true)
    MovieScore getMovieScoreByuIdAndMId(Integer uId, Integer mId);
}
