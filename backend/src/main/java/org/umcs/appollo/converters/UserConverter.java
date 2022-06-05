package org.umcs.appollo.converters;

import org.umcs.appollo.model.RoleEntity;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;

import java.util.LinkedList;


public class UserConverter {

    public User FromEntityToApi(UserEntity userEntity) {
        if (userEntity == null)
            return null;

        User out = new User();

        out.setId(userEntity.getId());
        out.setUsername(userEntity.getUsername());
        out.setPassword(userEntity.getPassword());
        out.setEmail(userEntity.getEmail());
        out.setFirstname(userEntity.getFirstName());
        out.setLastname(userEntity.getLastName());

        return out;
    }

    public UserEntity FromApiToEntity(User user) {
        if (user == null)
            return null;

        UserEntity out = new UserEntity();

        out.setId(user.getId());
        out.setUsername(user.getUsername());
        out.setPassword(user.getPassword());
        out.setEmail(user.getEmail());
        out.setFirstName(user.getFirstname());
        out.setLastName(user.getLastname());
        return out;
    }
}
