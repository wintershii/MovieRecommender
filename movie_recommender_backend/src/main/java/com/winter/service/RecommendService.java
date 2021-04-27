package com.winter.service;

import com.winter.common.RedisPoolUtil;
import com.winter.domain.MoviePredict;
import org.json.JSONArray;
import org.json.JSONObject;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;

import static com.winter.common.HttpClient.asyncSinglePostRequest;
@Service
public class RecommendService {

    @Resource(name = "redissonClient")
    private RedissonClient redissonClient;

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
        List<String> userTags = redis.lrange("user_tag_" + uid, 0, -1);
        System.out.println(userTags);
        for (String tag : userTags) {
            String key = "movie_tag_" + tag;
            String tagMoviesString = redis.get(key);
            if (tagMoviesString == null) {
                continue;
            }
            List<String> tagMovies = Arrays.asList(tagMoviesString.split(","));
            System.out.println(tagMovies);
            recallResult.addAll(tagMovies);
        }
        System.out.println(recallResult);
        // 4.转换&消重
        Set<Integer> midSet = new HashSet<>();
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(uid.toString());
        bloomFilter.tryInit(1000, 0.003);
        for (String midStr : recallResult) {
            Integer mid = Integer.valueOf(midStr);
            if (!bloomFilter.contains(mid)) {
                midSet.add(mid);
            }
        }
        redis.close();
        System.out.println(midSet);
        return new ArrayList<>(midSet);
    }

    // 排序
    public List<Integer> predict(Integer uid, List<Integer> mids) {
        JSONArray instances = new JSONArray();
        List<MoviePredict> moviePredicts = new LinkedList<>();
        for (Integer mid : mids) {
            JSONObject instance = new JSONObject();
            //为每个样本添加特征，userId和movieId
            instance.put("userId", uid);
            instance.put("movieId", mid);
            instances.put(instance);
            MoviePredict moviePredict = new MoviePredict(mid, 0.0);
            moviePredicts.add(moviePredict);
        }
        JSONObject instancesRoot = new JSONObject();
        instancesRoot.put("instances", instances);
        System.out.println(instancesRoot.toString());
        //请求TensorFlow Serving API
        String predictionScores = asyncSinglePostRequest("http://localhost:8501/v1/models/recmodel:predict", instancesRoot.toString()); //获取返回预估值
        JSONObject predictionsObject = new JSONObject(predictionScores);
        JSONArray scores = predictionsObject.getJSONArray("predictions");
        System.out.println(scores);
        for (int i = 0; i < moviePredicts.size(); ++i) {
            MoviePredict moviePredict = moviePredicts.get(i);
            moviePredict.setPredictScore(scores.getJSONArray(i).getDouble(0));
        }
        Collections.sort(moviePredicts, (o1, o2) -> o1.getPredictScore() > o2.getPredictScore() ? -1 : 1);
//        System.out.println(moviePredicts);
        int size = 9;
        if (moviePredicts.size() < 10) {
            size = moviePredicts.size() - 1;
        }
        List<MoviePredict> top10 = moviePredicts.subList(0, size);
//        System.out.println(top10);
        Collections.shuffle(top10);
        List<Integer> predictResult = new ArrayList<>();
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(uid.toString());
        bloomFilter.tryInit(1000, 0.003);
        for (int i = 0; i < top10.size(); ++i) {
            Integer mid = top10.get(i).getMid();
            predictResult.add(mid);
            bloomFilter.add(mid);
        }
        return predictResult;
    }
}
