package com.winter.service;

import com.winter.dao.MovieCollectDao;
import com.winter.dao.MovieDao;
import com.winter.dao.MovieDetailDao;
import com.winter.dao.MovieScoreDao;
import com.winter.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieDetailDao movieDetailDao;

    @Autowired
    private MovieScoreDao movieScoreDao;

    @Autowired
    private MovieCollectDao movieCollectDao;

    public List<Movie> getMovies() {
        List<Movie> movieList = movieDao.findAll();
        if (movieList == null) {
            return new ArrayList<>();
        }
        return movieList;
    }

    public MovieVo getMovieDetail(Integer mId) {
        //109  155
        Movie movie = movieDao.getMovieById(mId);
        MovieDetail movieDetail = movieDetailDao.getBymId(mId);
        MovieVo vo = new MovieVo();
        vo.setId(mId);
        vo.setName(movie.getName());
        vo.setPeoples(movie.getPeoples());
        vo.setScore(movie.getScore());
        vo.setPicUrl(movie.getPicUrl());
        vo.setDescribe(movie.getDescribe());
        vo.setActor(movieDetail.getActor());
        List<String> comments = Arrays.asList(movieDetail.getComments().split("\t"));
        vo.setComments(comments);
        vo.setDirector(movieDetail.getDirector());
        vo.setPlot(movieDetail.getPlot());
        return vo;
    }

    public Integer getMovieScore(Integer uid, Integer mid) {
        MovieScore movieScore = movieScoreDao.getMovieScoreByuIdAndMId(uid, mid);
        if (movieScore == null) {
            return 0;
        }
        return movieScore.getScore();
    }

    public boolean setMovieScore(Integer uid, Integer mid, Integer score) {
        MovieScore movieScore = new MovieScore();
        movieScore.setuId(uid);
        movieScore.setmId(mid);
        movieScore.setScore(score);
        MovieScore res = movieScoreDao.save(movieScore);
        if (res == null) {
            return false;
        }
        // 判断该电影是否在想看列表，如果再则标记删除
        if (movieCollectDao.existsByUidAndMid(uid, mid)) {
            movieCollectDao.update(uid, mid);
        }
        return true;
    }

    public boolean collectMovie(Integer uid, Integer mid) {
        MovieCollect movieCollect = new MovieCollect();
        movieCollect.setUid(uid);
        movieCollect.setMid(mid);
        movieCollect.setDeleted(0);
        MovieCollect res = movieCollectDao.save(movieCollect);
        if (res != null) {
            return true;
        }
        return false;
    }

    public List<Movie> getCollectMovies(Integer uid) {
        List<MovieCollect> midList = movieCollectDao.getMovieCollectsByUidAndDeleted(uid, 0);
        List<Movie> list = new ArrayList<>();
        for (MovieCollect movieCollect : midList) {
            list.add(movieDao.getMovieById(movieCollect.getMid()));
        }
        return list;
    }

}
