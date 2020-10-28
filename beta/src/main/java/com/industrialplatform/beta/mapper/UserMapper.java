package com.industrialplatform.beta.mapper;

import com.industrialplatform.beta.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryUserByName(String username);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

}


