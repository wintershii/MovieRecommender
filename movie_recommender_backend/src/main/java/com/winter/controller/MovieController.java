package com.winter.controller;

import com.winter.common.ResponseCode;
import com.winter.common.ServerResponse;
import com.winter.domain.Movie;
import com.winter.domain.User;
import com.winter.service.MovieService;
import com.winter.service.UserService;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "/top250", method = RequestMethod.GET)
    public ServerResponse getMovies(String username) {
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
        if (id == null || id < 2 || id > 251) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        
    }
}
