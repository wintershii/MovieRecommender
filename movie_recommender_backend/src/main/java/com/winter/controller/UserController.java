package com.winter.controller;

import com.winter.common.MD5Utils;
import com.winter.common.ResponseCode;
import com.winter.common.ServerResponse;
import com.winter.domain.User;
import com.winter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password ) {
//        String username = json.get("username");
//        String password = json.get("password");
        LOG.info(username + " " + password);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = userService.getUserByName(username);
        if (user.getPassword().equals(MD5Utils.getPwd(password))) {
            user.setPassword("");
            return ServerResponse.createBySuccessCodeMessage("登陆成功!", user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "密码错误!");
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse register(User user) {
        LOG.info(user.toString());
        if (StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPassword())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        user.setPassword(MD5Utils.getPwd(user.getPassword()));
        boolean isSuccess = userService.save(user);
        if (isSuccess) {
            return ServerResponse.createBySuccessMessage("注册成功!");
        }
        return ServerResponse.createByErrorMessage("注册失败!");
    }
}
