package com.anshare.project.dao;

import com.anshare.project.core.Mapper;
import com.anshare.project.model.SqlModel;
import com.anshare.project.model.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper extends Mapper<Users> {

    List<Users> list(SqlModel sqlModel);

    String findNumber(@Param("workGroupID") String workGroupID, @Param("number") String number);

    void clearUsers();

    String getGroupName(String username);

    String findName(String username);

    String getInfo(String username);

    void clearPatrolData();

    void usersMapper();
}