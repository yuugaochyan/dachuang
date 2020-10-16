package com.industrialplatform.beta.controller;

import com.industrialplatform.beta.mapper.UserMapper;
import com.industrialplatform.beta.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@Validated
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
    public boolean queryUserByName(@RequestBody User user){
        return (user.getUserpassword().equals(userMapper.queryUserByName(user.getUsername()).getUserpassword()));
    }

    //增加新用户
    @PostMapping("/addUser")
    public boolean addUser(@RequestBody User user){
        if (userMapper.queryUserByName(user.getUsername())==null)
        {   userMapper.addUser(user);
            return true;
        }
        else{
            //用户名重复
            return false;
        }
    }

    //注册test
    @RequestMapping(value = "/addUsertest/{username}/{userpassword}/{useremail}" , method = RequestMethod.GET)
    public boolean addUsertest(@Validated User user){
        if (userMapper.queryUserByName(user.getUsername())==null)
        {   userMapper.addUser(user);
            System.out.println(user);
            return true;
        }
        else{
            //用户名重复
            return false;
        }
    }



    //更改用户属性
    @GetMapping("/updateUser")
    public String updateUser(User user){
        userMapper.updateUser(new User(20,"Kiro","000000","123456@qq.com"));
        return "修改成功！";
    }

    //删除用户
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userMapper.deleteUser(id);
        return "删除成功！";
    }


}
