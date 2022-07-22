package com.ilxqx.springsecuritylearning.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

/**
 * 短信登录认证提供器
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 23:52
 */
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1、验证码从存储介质中拿出来，怎么拿？
        // 2、根据手机号去拿
        // 3、比对验证码是否一样
        // 4、如果一样，那么认证成功
        // 5、否则，那就认证失败，认证失败就需要抛出BadCredentialException
        String mobilePhone = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        log.info("手机号【{}】验证码【{}】开始登录", mobilePhone, code);
        if (StringUtils.equals(code, "123456")) {
            return new SmsCodeAuthenticationToken(mobilePhone, code, Collections.emptyList());
        }

        throw new BadCredentialsException("验证码错误");
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
