package com.mission.repository;

import com.mission.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberRepository extends CrudRepository<Member, Long> {

  @Transactional(readOnly = true)
  Optional<Member> findByEmail(String email);

}
