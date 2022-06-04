package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umcs.appollo.model.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {
}
