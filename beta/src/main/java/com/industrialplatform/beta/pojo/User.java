package com.industrialplatform.beta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private  String userpassword;
    @Email(message = "输入的邮箱格式不正确！")
    private  String useremail;

    User(String name,String password,String email){
        username=name;
        userpassword=password;
        useremail=email;
        id=0;
    }
}
