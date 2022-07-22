package com.ilxqx.springsecuritylearning.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.UUID;

/**
 * 框架级别的安全上下文仓库实现
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/7/9 23:03
 */
@Component
@RequiredArgsConstructor
public class FrameworkRedisSecurityContextRepository implements SecurityContextRepository {

    private static final String AUTHENTICATION_CACHE_KEY_PREFIX = "security:authentication:";
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Obtains the security context for the supplied request. For an unauthenticated user,
     * an empty context implementation should be returned. This method should not return
     * null.
     * <p>
     * The use of the <tt>HttpRequestResponseHolder</tt> parameter allows implementations
     * to return wrapped versions of the request or response (or both), allowing them to
     * access implementation-specific state for the request. The values obtained from the
     * holder will be passed on to the filter chain and also to the <tt>saveContext</tt>
     * method when it is finally called to allow implicit saves of the
     * <tt>SecurityContext</tt>. Implementations may wish to return a subclass of
     * {@link SaveContextOnUpdateOrErrorResponseWrapper} as the response object, which
     * guarantees that the context is persisted when an error or redirect occurs.
     * Implementations may allow passing in the original request response to allow
     * explicit saves.
     *
     * @param requestResponseHolder holder for the current request and response for which
     *                              the context should be loaded.
     * @return The security context which should be used for the current request, never
     * null.
     * @deprecated Use {@link #loadContext(HttpServletRequest)} instead.
     */
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader("Authorization");

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if (StringUtils.isBlank(authorization)) {
            return securityContext;
        }

        ValueOperations<String, Object> valueOperations = this.redisTemplate.opsForValue();
        Authentication authentication = (Authentication) valueOperations.get(AUTHENTICATION_CACHE_KEY_PREFIX + authorization);

        if (authentication == null) {
            return securityContext;
        }

        securityContext.setAuthentication(authentication);
        return securityContext;
    }

    /**
     * Stores the security context on completion of a request.
     *
     * @param context  the non-null context which was obtained from the holder.
     * @param request
     * @param response
     */
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        ValueOperations<String, Object> valueOperations = this.redisTemplate.opsForValue();
        String accessToken = UUID.randomUUID().toString();
        valueOperations.set(AUTHENTICATION_CACHE_KEY_PREFIX + accessToken, context.getAuthentication(), Duration.ofMinutes(2));

        request.setAttribute("accessToken", accessToken);
    }

    /**
     * Allows the repository to be queried as to whether it contains a security context
     * for the current request.
     *
     * @param request the current request
     * @return true if a context is found for the request, false otherwise
     */
    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (StringUtils.isBlank(authorization)) {
            return false;
        }

        return BooleanUtils.isTrue(
            this.redisTemplate.hasKey(AUTHENTICATION_CACHE_KEY_PREFIX + authorization)
        );
    }
}
