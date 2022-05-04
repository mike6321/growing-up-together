package com.mission.repository;

import com.mission.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

}

