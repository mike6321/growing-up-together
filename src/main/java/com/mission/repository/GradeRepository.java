package com.mission.repository;

import com.mission.domain.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GradeRepository extends CrudRepository<Grade, Long> {}
