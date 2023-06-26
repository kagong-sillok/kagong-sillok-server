package org.prography.kagongsillok.auth.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.domain.RefreshTokenRepository;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.prography.kagongsillok.common.exception.SerializingException;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisListRepository implements RefreshTokenRepository {

    private final static String MEMBER_REFRESH_TOKEN_KEY_PREFIX = "member::refreshToken::";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public RefreshToken save(final RefreshToken refreshToken) {
        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        final String key = MEMBER_REFRESH_TOKEN_KEY_PREFIX + refreshToken.getMemberId();
        listOperations.leftPush(key, serializeRefreshToken(refreshToken));
        final long ttlSeconds = Duration.between(ZonedDateTime.now(), refreshToken.getExpire())
                .getSeconds();
        redisTemplate.expire(key, ttlSeconds, TimeUnit.SECONDS);
        return refreshToken;
    }

    @Override
    public List<RefreshToken> findByMemberId(final Long memberId) {
        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        final String key = MEMBER_REFRESH_TOKEN_KEY_PREFIX + memberId;
        final List<String> serializedRefreshTokens = listOperations.range(key, 0, -1);

        return CustomListUtils.mapTo(serializedRefreshTokens, this::deserializeRefreshToken);
    }

    @Override
    public void removeOldRefreshToken(final Long memberId) {
        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        final String key = MEMBER_REFRESH_TOKEN_KEY_PREFIX + memberId;
        listOperations.rightPop(key);
    }

    @Override
    public void removeRefreshToken(final Long memberId, final String deletedRefreshTokenId) {
        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        final String key = MEMBER_REFRESH_TOKEN_KEY_PREFIX + memberId;
        final List<String> serializedRefreshTokens = listOperations.range(key, 0, -1);
        final List<RefreshToken> refreshTokens = getRemainRefreshTokens(deletedRefreshTokenId, serializedRefreshTokens);

        redisTemplate.delete(key);
        refreshTokens.forEach(it -> listOperations.leftPush(key, serializeRefreshToken(it)));
        setExpire(key, refreshTokens);
    }

    private void setExpire(final String key, final List<RefreshToken> refreshTokens) {
        refreshTokens.stream()
                .sorted((o1, o2) -> o2.getExpire().compareTo(o1.getExpire()))
                .limit(1)
                .findAny()
                .map(RefreshToken::getExpire)
                .ifPresent(it -> {
                    final long ttlSeconds = Duration.between(ZonedDateTime.now(), it)
                            .getSeconds();
                    redisTemplate.expire(key, ttlSeconds, TimeUnit.SECONDS);
                });
    }

    private List<RefreshToken> getRemainRefreshTokens(
            final String deletedRefreshTokenId,
            final List<String> serializedRefreshTokens
    ) {
        return CustomListUtils.mapTo(serializedRefreshTokens, this::deserializeRefreshToken)
                .stream()
                .filter(refreshToken -> isValidRefreshToken(deletedRefreshTokenId, refreshToken))
                .toList();
    }

    private boolean isValidRefreshToken(final String deletedRefreshTokenId, final RefreshToken refreshToken) {
        return isNotDeletedRefreshToken(deletedRefreshTokenId, refreshToken) && refreshToken.isNotExpired();
    }

    private boolean isNotDeletedRefreshToken(final String deletedRefreshTokenId, final RefreshToken refreshToken) {
        return !refreshToken.getId().equals(deletedRefreshTokenId);
    }

    private String serializeRefreshToken(final RefreshToken refreshToken) {
        try {
            return objectMapper.writeValueAsString(refreshToken);
        } catch (JsonProcessingException e) {
            throw new SerializingException(
                    "RefreshToken을 json으로 직렬화하는 데에 에러가 발생했습니다. refreshToken = " + refreshToken
            );
        }
    }

    private RefreshToken deserializeRefreshToken(final String serializedRefreshToken) {
        try {
            return objectMapper.readValue(serializedRefreshToken, RefreshToken.class);
        } catch (JsonProcessingException e) {
            throw new SerializingException(
                    "json에서 RefreshToken으로 역직렬화하는 데에 에러가 발생했습니다. serializedRefreshToken = "
                            + serializedRefreshToken
            );
        }
    }
}
