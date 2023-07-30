package org.prography.kagongsillok.common.resolver;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.infrastructure.JwtAuthTokenProvider;
import org.prography.kagongsillok.common.resolver.dto.MemberIdDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberIdDto.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String accessToken = getAccessToken(httpServletRequest.getHeader("authorization"));
        Long memberId = jwtAuthTokenProvider.getLoginMemberByAccessToken(accessToken).getMemberId();

        return new MemberIdDto(memberId);
    }

    private String getAccessToken(String accessTokenHeader) {
        return accessTokenHeader.split(" ")[1];
    }
}
