package com.ilxqx.springsecuritylearning.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * JWT安全上下文仓库实现
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/7/11 22:09
 */
@Component
public class FrameworkJwtSecurityContextRepository implements SecurityContextRepository {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
        UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)
    );

    private final JwtParser PARSER = Jwts.parserBuilder()
        .setSigningKey(
            secretKey
        )
        .build();

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
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        if (StringUtils.isBlank(authorization)) {
            return securityContext;
        }

        Jws<Claims> claimsJws;
        try {
            claimsJws = PARSER.parseClaimsJws(authorization);
        } catch (ExpiredJwtException e) {
            return securityContext;
        }
        Claims claims = claimsJws.getBody();

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities")), (String) claims.get("userId"));
        securityContext.setAuthentication(authenticationToken);

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
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String accessToken = Jwts.builder()
            .signWith(secretKey)
            .claim("userId", userDetails.getUsername())
            .claim("authorities", String.join(
                ",",
                AuthorityUtils.authorityListToSet(context.getAuthentication().getAuthorities())
            ))
            .setExpiration(
                new Date(
                    System.currentTimeMillis() + Duration.ofMinutes(60).toMillis()
                )
            )
            .compact();

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
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authorization)) {
            return false;
        }

        try {
            PARSER.parseClaimsJws(authorization);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
