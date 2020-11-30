package com.winter.service;

import com.winter.dao.MovieDao;
import com.winter.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieDao movieDao;

    public List<Movie> getMovies() {
        List<Movie> movieList = movieDao.findAll();
        if (movieList == null) {
            return new ArrayList<>();
        }
        return movieList;
    }

}
