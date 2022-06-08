package org.umcs.appollo.services;

import org.umcs.appollo.model.RoleEntity;

public interface RoleService {
    RoleEntity findByName(String name);
}
