package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umcs.appollo.model.PollEntity;

public interface PollRepository extends JpaRepository<PollEntity, Integer> {
}
