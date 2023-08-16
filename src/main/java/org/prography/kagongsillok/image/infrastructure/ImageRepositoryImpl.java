package org.prography.kagongsillok.image.infrastructure;

import static org.prography.kagongsillok.image.domain.QImage.image;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isExistIdIn(final List<Long> imageIds) {
        List<Long> existImageIds = queryFactory
                .selectFrom(image)
                .where(
                        isNotDeleted()
                )
                .fetch()
                .stream()
                .map(i -> i.getId())
                .collect(Collectors.toList());

        return imageIds.stream()
                .map(imageId -> existImageIds.contains(imageId))
                .collect(Collectors.toList())
                .contains(Boolean.FALSE);
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return image.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return image.isDeleted.eq(Boolean.FALSE);
    }
}
