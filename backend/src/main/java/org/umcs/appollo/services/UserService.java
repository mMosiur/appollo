package org.umcs.appollo.services;

import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;

import java.util.List;

public interface UserService {
    User addNew(User user);
    List<User> findAll();
    User findOne(String username);
    User edit(String username);
    void delete(String username);


}
