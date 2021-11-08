package org.navistack.admin.support.security;

import org.navistack.admin.modules.common.entity.Privilege;
import org.navistack.admin.modules.common.entity.User;
import org.navistack.admin.modules.system.service.AuthenticationService;
import org.navistack.admin.modules.system.service.AuthorityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final AuthenticationService authenticationService;
    private final AuthorityService authorityService;

    public UserDetailsService(AuthenticationService authenticationService, AuthorityService authorityService) {
        this.authenticationService = authenticationService;
        this.authorityService = authorityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationService.findUserByLoginName(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Username Or Password Incorrect"));
    }

    protected UserDetails createUserDetails(User user) {
        List<Privilege> userPrivileges = authorityService.listGrantedPrivilegesOf(user);

        List<GrantedAuthority> authorities = userPrivileges.stream()
                .map(Privilege::getCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        LoginUser principal = new LoginUser();
        principal.setUsername(user.getLoginName());
        principal.setId(user.getId());
        principal.setPassword(user.getPassword());
        principal.setAuthorities(authorities);
        return principal;
    }
}
