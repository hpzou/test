package com.anshare.project.service;

import com.anshare.project.core.Service;
import com.anshare.project.model.Users;


/**
 * Created by Anshare on 2019/01/22.
 */
public interface UsersService extends Service<Users> {

    String findNumber(String workGroupID, String number);

    void clearUsers();

    String getGroupName(String username);

    String findName(String username);

    String getInfo(String username);

    void clearPatrolData();

    void updatePassword();
}
