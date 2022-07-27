package com.ilxqx.springsecuritylearning.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Jwt访问Token
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/7/11 22:15
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;

    public JwtAuthenticationToken(String userId) {
        super(Collections.emptyList());
        this.userId = userId;
        setAuthenticated(true);
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String userId) {
        super(authorities);
        this.userId = userId;
        setAuthenticated(true);
    }

    /**
     * The credentials that prove the principal is correct. This is usually a password,
     * but could be anything relevant to the <code>AuthenticationManager</code>. Callers
     * are expected to populate the credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * The identity of the principal being authenticated. In the case of an authentication
     * request with username and password, this would be the username. Callers are
     * expected to populate the principal for an authentication request.
     * <p>
     * The <tt>AuthenticationManager</tt> implementation will often return an
     * <tt>Authentication</tt> containing richer information as the principal for use by
     * the application. Many of the authentication providers will create a
     * {@code UserDetails} object as the principal.
     *
     * @return the <code>Principal</code> being authenticated or the authenticated
     * principal after authentication.
     */
    @Override
    public Object getPrincipal() {
        return this.userId;
    }
}
