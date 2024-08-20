package com.samsung.bookmanagerment.service.user;


import com.samsung.bookmanagerment.entity.User;

import java.util.List;

public interface UserService {

    /////////////////////User///////////

    User signin(User request) throws Exception;

    User signup(User request) throws Exception;



    /////////////////////Admin///////////
    User adminAddUser(User request) throws Exception;

    User adminSignin(User request) throws Exception;

}
