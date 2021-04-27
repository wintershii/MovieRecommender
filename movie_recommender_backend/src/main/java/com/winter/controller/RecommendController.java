package com.winter.controller;
import com.winter.common.RedisPoolUtil;
import com.winter.common.ServerResponse;
import com.winter.domain.Movie;
import com.winter.service.MovieService;
import com.winter.service.RecommendService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private MovieService movieService;

    public static void main(String[] args) {
//        JSONArray instances = new JSONArray();
//        JSONObject instance = new JSONObject();
//        instance.put("userId", 123); instance.put("movieId", 123);
//        instances.put(instance);
//        JSONObject instancesRoot = new JSONObject();
//        instancesRoot.put("instances", instances);
//        System.out.println(instancesRoot.toString());
        Jedis redis = RedisPoolUtil.getInstance();
        List<String> list = redis.lrange("recall_item2item_7", 0,-1);
        System.out.println(list);
    }

    @RequestMapping("/sort")
    public ServerResponse recommend(Integer uid) {
        // 1.召回
        List<Integer> recallResult = recommendService.recall(uid);
        // 2.排序
        List<Integer> predictResult = recommendService.predict(uid, recallResult);
        List<Movie> movieList = movieService.getMovieByMidList(predictResult);
        List<List<String>> res = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> pics = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            if (i >= movieList.size()) {
                break;
            }
            pics.add(movieList.get(i).getPicUrl());
            names.add(movieList.get(i).getName());
            ids.add(movieList.get(i).getId().toString());
        }
        res.add(pics);
        res.add(names);
        res.add(ids);
        return ServerResponse.createBySuccess(res);
    }
}
