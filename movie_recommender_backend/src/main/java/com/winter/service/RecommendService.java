package com.winter.service;

import com.winter.common.RedisPoolUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecommendService {

    // 召回
    public List<Integer> recall(Integer uid) {
        Jedis redis = RedisPoolUtil.getInstance();
        List<String> recallResult = new ArrayList<>();
        // 1.item2item
        List<String> item2item = redis.lrange("recall_item2item_" + uid, 0,-1);
        recallResult.addAll(item2item);
        // 2.als
        List<String> als = redis.lrange("recall_item2item_" + uid, 0,-1);
        recallResult.addAll(als);
        // 3.cold_start
        List<String> userTags = redis.lrange("user_tag" + uid, 0, -1);
        for (String tag : userTags) {
            String key = "movie_tag" + tag;
            String tagMoviesString = redis.get(key);
            List<String> tagMovies = Arrays.asList(tagMoviesString.split(","));
            recallResult.addAll(tagMovies);
        }
        // 4.转换&消重

        redis.close();
        return null;
    }


    // 排序
    public List<Integer> predict(Integer uid, List<Integer> mids) {
        return null;
    }
}
