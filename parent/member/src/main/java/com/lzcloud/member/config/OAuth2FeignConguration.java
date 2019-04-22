package com.lzcloud.member.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @BelongsProject: lzcloud
 * @BelongsPackage: com.lzcloud.member.com.lzcloud.zipkin.config
 * @Author: lzheng
 * @DATE: 2019/4/19 10:31
 * @Description:
 */
public class OAuth2FeignConguration implements RequestInterceptor {

    public static final String BEARER = "Bearer";

    public static final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
            template.header(AUTHORIZATION, String.format("%s %s", BEARER, oAuth2AuthenticationDetails.getTokenValue()));
        }
    }
}
