package com.winter.controller;

import com.winter.common.MD5Utils;
import com.winter.common.ResponseCode;
import com.winter.common.ServerResponse;
import com.winter.domain.User;
import com.winter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password) {
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
