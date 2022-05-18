package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umcs.appollo.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}