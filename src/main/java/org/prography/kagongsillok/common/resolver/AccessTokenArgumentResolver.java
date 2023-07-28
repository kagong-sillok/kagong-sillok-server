package org.prography.kagongsillok.common.resolver;

import javax.servlet.http.HttpServletRequest;
import org.prography.kagongsillok.common.resolver.dto.AccessTokenDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(AccessTokenDto.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String accessToken = getAccessToken(httpServletRequest.getHeader("authorization"));

        return new AccessTokenDto(accessToken);
    }

    private String getAccessToken(String accessTokenHeader) {
        return accessTokenHeader.split(" ")[1];
    }
}
