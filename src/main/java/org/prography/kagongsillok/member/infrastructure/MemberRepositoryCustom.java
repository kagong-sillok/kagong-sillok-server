package org.prography.kagongsillok.member.infrastructure;

import java.util.List;
import org.prography.kagongsillok.member.domain.Member;

public interface MemberRepositoryCustom {

    List<Member> findByIdIn(final List<Long> memberIds);

}
