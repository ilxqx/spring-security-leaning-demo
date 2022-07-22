package com.ilxqx.springsecuritylearning.support;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * 短信验证码登录配置器的第二种实现
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/22 20:59
 */
public class SmsCodeLoginConfigurer2<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<SmsCodeLoginConfigurer2<B>, B> {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(B builder) throws Exception {
        SmsCodeAuthenticationFilter authenticationFilter = new SmsCodeAuthenticationFilter("/smsCodeLogin");
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        authenticationFilter.setAuthenticationDetailsSource(new WebAuthenticationDetailsSource());

        builder.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public final SmsCodeLoginConfigurer2<B> successHandler(AuthenticationSuccessHandler successHandler) {
        this.authenticationSuccessHandler = successHandler;
        return this;
    }

    public final SmsCodeLoginConfigurer2<B> failureHandler(AuthenticationFailureHandler failureHandler) {
        this.authenticationFailureHandler = failureHandler;
        return this;
    }
}
