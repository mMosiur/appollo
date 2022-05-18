package org.umcs.appollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umcs.appollo.model.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
}
