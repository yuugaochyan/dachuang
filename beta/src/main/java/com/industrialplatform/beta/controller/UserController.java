package com.industrialplatform.beta.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.industrialplatform.beta.mapper.UserMapper;
import com.industrialplatform.beta.pojo.User;
import com.industrialplatform.beta.service.UserService;
import com.industrialplatform.beta.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  UserService userService;

    @GetMapping("/queryUserList")
    public List<User> queryUserList(){
        List<User> userList=userMapper.queryUserList();
        for(User user:userList){
            System.out.println(user);
        }
        return userList;
    }

    //通过id查询用户（登录）
    @RequestMapping(value = "/queryUserByName",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryUserByName(User user){
        Map<String, Object> map=new HashMap<>();
        try{
                User userdb= userService.login(user);
                Map<String,String> payload = new HashMap<>();
                payload.put("id",String.valueOf(userdb.getId()));
                payload.put("name",userdb.getUsername());
                //生成JWT令牌
            String token= JWTUtils.getToken(payload);
            map.put("state",true);
            map.put("msg","认证成功");
            map.put("token",token);
            map.put("userID",userdb.getId());
        }catch ( Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    //增加新用户   前端接口需改为User对应变量名
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addUser(User user){
        Map<String,Object> map=new HashMap<>();
        if (userService.Register(user))
        {
            map.put("status",200);
            map.put("msg","注册成功!");
        }
        else{
            map.put("status",404);
            map.put("msg","注册失败！");
        }
        return map;
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

    //token信息检测test
    @PostMapping("/tokentest")
    public Map<String,Object>tokentest(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        String token=request.getHeader("token");
        DecodedJWT verify=JWTUtils.getTokenInfo(token);
        System.out.println("用户Id:"+verify.getClaim("id").asString());
        System.out.println("用户name:"+verify.getClaim("name").asString());
        map.put("state",true);
        map.put("msg","请求成功！");
        return map;
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
