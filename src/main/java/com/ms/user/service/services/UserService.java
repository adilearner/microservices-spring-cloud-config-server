package com.ms.user.service.services;

import com.ms.user.service.entities.User;

import java.util.List;

public interface UserService {
    //User Operations
    //Create
    User saveUser(User user);

    //getAllUser
    List<User> getAllUser();

    //get single user with userID
    User getUser(String userId);

    //delete user
    void deleteUser(String userId);



}
