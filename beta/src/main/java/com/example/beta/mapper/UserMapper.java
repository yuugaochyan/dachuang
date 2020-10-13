package com.example.beta.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pojo.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();
}

 /*  User queryUserById(int id);

    String addUser(User user);

    String updateUser(User user);

    String deleteUser(int id);
}

*/

