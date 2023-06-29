package org.prography.kagongsillok.tag.infrastructure;

import static org.prography.kagongsillok.tag.domain.QTag.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.tag.domain.Tag;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tag> findAllTags() {
        return queryFactory
                .selectFrom(tag)
                .where(
                        tag.isDeleted.eq(false)
                )
                .fetch();
    }
}
