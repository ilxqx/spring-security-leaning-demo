package com.ilxqx.springsecuritylearning.support;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 验证码登录配置器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:59
 */
public class SmsCodeLoginConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractAuthenticationFilterConfigurer<H, SmsCodeLoginConfigurer<H>, SmsCodeAuthenticationFilter> {

    /**
     * Creates a new instance
     *
     * @param authenticationFilter      the {@link AbstractAuthenticationProcessingFilter} to
     *                                  use
     * @param defaultLoginProcessingUrl the default URL to use for
     *                                  {@link #loginProcessingUrl(String)}
     */
    public SmsCodeLoginConfigurer(String defaultLoginProcessingUrl) {
        super(new SmsCodeAuthenticationFilter(defaultLoginProcessingUrl), defaultLoginProcessingUrl);
    }

    /**
     * Create the {@link RequestMatcher} given a loginProcessingUrl
     *
     * @param loginProcessingUrl creates the {@link RequestMatcher} based upon the
     *                           loginProcessingUrl
     * @return the {@link RequestMatcher} to use based upon the loginProcessingUrl
     */
    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }
}
