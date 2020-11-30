package com.winter.service;

import com.winter.dao.UserDao;
import com.winter.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByName(String name) {
        List<User> userList = userDao.findByName(name);
        if (userList == null || userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public boolean save(User user) {
        if (user.getAge() == null) {
            user.setAge(18);
        }
        if (user.getGender() == null) {
            user.setGender(0);
        }
        User u = userDao.save(user);
        if (u == null) {
            return false;
        }
        return true;
    }
}
