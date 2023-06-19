package org.prography.kagongsillok.auth.infrastructure;

import org.prography.kagongsillok.auth.infrastructure.dto.KakaoUserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoResourceApiFeignClient", url = "${oauth.kakao.resourceServerHost}")
public interface KakaoResourceApiFeignClient {

    @GetMapping("/v2/user/me")
    KakaoUserFeignResponse getKakaoUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestHeader("Content-Type") String contentTypeHeader,
            @RequestParam("secure_resource") Boolean secureResource,
            @RequestParam("property_keys") String propertyKeys
    );
}
