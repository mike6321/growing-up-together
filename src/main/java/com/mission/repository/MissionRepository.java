package com.mission.repository;

import com.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MissionRepository extends JpaRepository<Mission, Long> {

    boolean existsBySubject(String subject);

}
