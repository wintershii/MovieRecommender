package com.winter.movie_recommender_backend;

import com.winter.dao.MovieDao;
import com.winter.dao.UserDao;
import com.winter.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MovieRecommenderBackendApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieDao movieDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSave() {
//        User user = new User();
//        user.setName("shidongxuan");
//        user.setAge(18);
//        user.setGender(0);
//        user.setPassword("123123");
        //this.userDao.save(user);

//        List<User> userList = userDao.findByName("shidongxuan");
//        System.out.println(userList);
        System.out.println(movieDao.findAll());
    }

}
