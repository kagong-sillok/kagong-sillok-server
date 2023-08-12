package org.prography.kagongsillok.member.domain;

import org.prography.kagongsillok.member.infrastructure.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}
