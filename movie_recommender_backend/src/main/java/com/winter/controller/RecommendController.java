package com.winter.controller;
import com.winter.common.RedisPoolUtil;
import com.winter.common.ServerResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
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


        // 2.排序
        return null;
    }
}
