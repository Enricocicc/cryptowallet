package com.enrico.cryptowallet.service;

import com.enrico.cryptowallet.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

    User updateUser(User user, long id);

    void deleteUser(long id);

    public boolean isExistingUser(String email);

    public boolean isUserAuthorized(String email, String password);

}
