package org.prography.kagongsillok.member.infrastructure;

import java.util.List;
import java.util.Map;
import org.prography.kagongsillok.member.domain.Member;

public interface MemberRepositoryCustom {

    Map<Long, Member> findByIdIn(final List<Long> memberIds);
}
