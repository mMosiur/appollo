package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umcs.appollo.model.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer> {
}
