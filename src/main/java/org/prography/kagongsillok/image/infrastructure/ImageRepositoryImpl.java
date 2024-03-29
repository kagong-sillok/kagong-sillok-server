package org.prography.kagongsillok.image.infrastructure;

import static org.prography.kagongsillok.image.domain.QImage.image;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Image> findByIdInToMap(final List<Long> imageIds) {

        final List<Image> images = queryFactory
                .selectFrom(image)
                .where(
                        idIn(imageIds),
                        isNotDeleted()
                )
                .fetch();

        return images
                .stream()
                .collect(Collectors.toMap(Image::getId, Function.identity()));
    }

    @Override
    public boolean isNotExistIdIn(final List<Long> imageIds) {
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

    @Override
    public Map<Long, Image> getImageMap(final List<Long> imageIds) {
        return queryFactory
                .selectFrom(image)
                .where(
                        idIn(imageIds),
                        isNotDeleted()
                )
                .fetch()
                .stream()
                .collect(Collectors.toMap(Image::getId, Function.identity()));
    }

    private BooleanExpression idIn(final List<Long> ids) {
        return image.id.in(ids);
    }

    private BooleanExpression isNotDeleted() {
        return image.isDeleted.eq(Boolean.FALSE);
    }
}
