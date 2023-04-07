package org.navistack.admin.support.security;

import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.system.service.AuthenticationService;
import org.navistack.admin.modules.system.service.AuthorityService;
import org.navistack.framework.security.TokenAuthentication;
import org.navistack.framework.security.jwt.DefaultJwtClaims;
import org.navistack.framework.security.jwt.JwtClaims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtPayloadResolver implements org.navistack.framework.security.jwt.JwtPayloadResolver {
    private static final String USER_UID_KEY = "uid";
    private final AuthenticationService authenticationService;
    private final AuthorityService authorityService;

    public JwtPayloadResolver(AuthenticationService authenticationService, AuthorityService authorityService) {
        this.authenticationService = authenticationService;
        this.authorityService = authorityService;
    }

    @Override
    public JwtClaims getClaims(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        JwtClaims claims = new DefaultJwtClaims();
        claims.putSubject(loginUser.getUsername());
        claims.put(USER_UID_KEY, loginUser.getId());
        return claims;
    }

    @Override
    public Authentication getAuthentication(JwtClaims claims) {
        Optional<User> user = authenticationService.findUserByLoginName(claims.getSubject());

        if (!user.isPresent()) {
            throw new UserNotFoundException("No such user found");
        }

        List<Privilege> privileges = authorityService.listGrantedPrivilegesOf(user.get());

        Set<? extends GrantedAuthority> authorities = privileges.stream()
                .map(Privilege::getCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        LoginUser principal = new LoginUser();
        principal.setUsername((claims.getSubject()));
        principal.setId(claims.get(USER_UID_KEY, Long.class));
        principal.setPassword("NO_PASSWORD");
        principal.setAuthorities(authorities);

        return TokenAuthentication.authenticated(principal, "NO_CREDENTIALS", authorities);
    }
}
