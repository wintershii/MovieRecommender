package com.winter.controller;

import com.winter.common.ResponseCode;
import com.winter.common.ServerResponse;
import com.winter.domain.Movie;
import com.winter.domain.MovieScore;
import com.winter.domain.MovieVo;
import com.winter.domain.User;
import com.winter.service.MovieService;
import com.winter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    private static final Logger LOG = LoggerFactory.getLogger(MovieController.class);

    @RequestMapping(value = "/top250", method = RequestMethod.GET)
    public ServerResponse getMovies(String username) {
        LOG.info(username + "getMovies");
        if (StringUtils.isBlank(username)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        User u = userService.getUserByName(username);
        if (u == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        List<Movie> movieList = movieService.getMovies();

        return ServerResponse.createBySuccess(movieList);
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public ServerResponse getMovieDetail(Integer id) {
        LOG.info("id:" + id + "getMovieDetail");
        if (id == null || id < 2 || id > 251) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        MovieVo movieVo = movieService.getMovieDetail(id);
        if (movieVo == null) {
            return ServerResponse.createByErrorMessage("请求失败");
        }
        return ServerResponse.createBySuccess(movieVo);
    }

    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public ServerResponse getMovieScore(Integer uid, Integer mid) {
        if (uid == null || mid == null || mid < 2 || mid > 251) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Integer score = movieService.getMovieScore(uid, mid);
        if (score == null) {
            score = 0;
        }
        return ServerResponse.createBySuccess(score);
    }

    @RequestMapping(value = "/scoring", method = RequestMethod.POST)
    public ServerResponse setMovieScore(Integer uid, Integer mid, Integer score) {
        if (uid == null || mid == null || mid < 2 || mid > 251 || score < 1 || score > 5) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (movieService.setMovieScore(uid, mid, score)) {
            return ServerResponse.createBySuccess(score);
        }
        return ServerResponse.createByErrorMessage("打分失败");
    }
}
