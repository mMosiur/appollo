package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.umcs.appollo.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    @Query( value = "SELECT r from Role r where r.name = ?1")
    RoleEntity findByName(String name);
}
