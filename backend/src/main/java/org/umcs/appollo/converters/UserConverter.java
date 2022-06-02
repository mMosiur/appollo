package org.umcs.appollo.converters;

import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;


public class UserConverter {

    public User FromEntityToApi(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        User result =  new User();
        // TODO: 02.06.2022 user konwerter 2
        return null;



    }

    public UserEntity FromApiToEntity(User user) {
        // TODO: 02.06.2022 user konwerter 1
        return null;

    }
}
