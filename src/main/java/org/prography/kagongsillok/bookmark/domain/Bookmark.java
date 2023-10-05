package org.prography.kagongsillok.bookmark.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.prography.kagongsillok.common.entity.AbstractRootEntity;

@Getter
@Entity
@Table(name = "bookmark", indexes = {
        @Index(name = "ix__bookmark__member_id", columnList = "memberId"),
        @Index(name = "ix__bookmark__place_id", columnList = "placeId")
})
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update bookmark set is_deleted = true, updated_at = now() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;
    private Long memberId;

    public Bookmark(final Long placeId, final Long memberId) {
        this.placeId = placeId;
        this.memberId = memberId;
    }
}
