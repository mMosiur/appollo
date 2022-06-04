package org.umcs.appollo.services;

import org.umcs.appollo.model.api.User;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    List<User> findAll();
    User findOne(int id);
    User edit(int id, User data);
    void delete(int id);
}
