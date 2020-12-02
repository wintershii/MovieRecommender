package com.winter.dao;

import com.winter.domain.Movie;
import com.winter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<Movie, Integer> {
    Movie getMovieById(Integer id);
}
