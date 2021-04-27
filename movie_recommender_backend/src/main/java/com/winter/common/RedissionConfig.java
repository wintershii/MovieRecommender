package com.winter.common;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;
/**
 * Redission配置类
 */
@Configuration
public class RedissionConfig {

    private static String redisConfigFile = "application.properties";

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redissonClient;
        Properties props = new Properties();
        //加载连接池配置文件
        try {
            props.load(RedissionConfig.class.getClassLoader().getResourceAsStream(redisConfigFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Config config = new Config();
        //starter依赖进来的redisson要以redis://开头，其他不用

        String url = "redis://" + props.getProperty("redis.ip") + ":" + props.getProperty("redis.port");
        config.useSingleServer().setAddress(url);

        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            System.out.printf("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }

    public static void main(String[] args) {
        RedissonClient redissonClient = new RedissionConfig().redissonClient();
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter("2");
        bloomFilter.tryInit(1000, 0.003);
//        bloomFilter.add(1);
//        bloomFilter.add(3);
//        bloomFilter.add(5);

        System.out.println(bloomFilter.contains(1));
        System.out.println(bloomFilter.contains(2));
        System.out.println(bloomFilter.contains(3));
        System.out.println(bloomFilter.contains(5));
        System.out.println(bloomFilter.contains(10));
        redissonClient.shutdown();
    }

}
