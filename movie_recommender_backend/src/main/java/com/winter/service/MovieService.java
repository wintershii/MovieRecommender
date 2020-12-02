package com.winter.service;

import com.winter.dao.MovieDao;
import com.winter.dao.MovieDetailDao;
import com.winter.domain.Movie;
import com.winter.domain.MovieDetail;
import com.winter.domain.MovieVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieDetailDao movieDetailDao;

    public List<Movie> getMovies() {
        List<Movie> movieList = movieDao.findAll();
        if (movieList == null) {
            return new ArrayList<>();
        }
        return movieList;
    }

    public MovieVo getMovieDetail(Integer mId) {
        //109  155
        Movie movie = movieDao.getMovieById(mId)
    }

}
