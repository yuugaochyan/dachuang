package com.industrialplatform.beta.service;

import com.industrialplatform.beta.mapper.UserMapper;
import com.industrialplatform.beta.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user){
        if(user.getUserpassword().equals(userMapper.queryUserByName(user.getUsername()).getUserpassword())){
            user.setId(userMapper.queryUserByName(user.getUsername()).getId());
            return user;
        }
        throw  new RuntimeException("登陆失败");
    }

}
