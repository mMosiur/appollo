package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.umcs.appollo.model.UserEntity;
import org.umcs.appollo.model.api.User;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query( value = "SELECT u from Users u where u.username = ?1")
    UserEntity findByUsername(String username);

    @Query(value = "SELECT u FROM Users u where u.email = ?1")
    UserEntity findByEmail(String email);
}