package com.anshare.project.service.impl;

import com.anshare.project.core.AbstractService;
import com.anshare.project.dao.UsersMapper;
import com.anshare.project.model.Users;
import com.anshare.project.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Anshare on 2019/01/22.
 */
@Service
@Transactional
public class UsersServiceImpl extends AbstractService<Users> implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public String findNumber(String workGroupID, String number) {
        return usersMapper.findNumber(workGroupID,number);
    }

    @Override
    public void clearUsers() {
       usersMapper.clearUsers();
    }

    @Override
    public String getGroupName(String username) {
        return usersMapper.getGroupName(username);
    }

    @Override
    public String findName(String username) {
        return usersMapper.findName(username);
    }

    @Override
    public String getInfo(String username) {
        return usersMapper.getInfo(username);
    }

    @Override
    public void clearPatrolData() {
        usersMapper.clearPatrolData();
    }

    @Override
    public void updatePassword() {
        usersMapper.usersMapper();
    }


}
