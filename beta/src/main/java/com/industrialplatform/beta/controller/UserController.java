package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.UserMapper;
import com.industrialplatform.beta.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/queryUserList")
    public List<User> queryUserList(){
        List<User> userList=userMapper.queryUserList();
        for(User user:userList){
            System.out.println(user);
        }
        return userList;
    }

    //通过id查询用户
    @PostMapping("/queryUserByName")
    public boolean queryUserByName(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println(password.equals(userMapper.queryUserByName(username).getUserpassword()));
        return (password.equals(userMapper.queryUserByName(username).getUserpassword()));
    }

    //增加新用户
    @PostMapping("/addUser")
    public boolean addUser(@RequestParam("username")String username,@RequestParam("password")String password){
        userMapper.addUser(new User(0,username,password));
        System.out.println(username+password);
        return true;
    }

    //更改用户属性
    @GetMapping("/updateUser")
    public String updateUser(User user){
        userMapper.updateUser(new User(20,"Kiro","000000"));
        return "修改成功！";
    }

    //删除用户
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userMapper.deleteUser(id);
        return "删除成功！";
    }


}
