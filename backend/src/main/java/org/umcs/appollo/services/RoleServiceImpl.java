package org.umcs.appollo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.umcs.appollo.model.RoleEntity;
import org.umcs.appollo.repository.RoleRepository;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String name) {
        RoleEntity role = roleRepository.findByName(name);
        return role;
    }
}
