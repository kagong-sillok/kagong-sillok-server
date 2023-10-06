package org.prography.kagongsillok.bookmark.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByPlaceIdAndMemberIdAndIsDeletedFalse(Long placeId, Long memberId);
}
