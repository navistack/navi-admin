package org.navistack.admin.modules.system.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.navistack.admin.modules.system.web.rest.vm.PasswordLoginVm;
import org.navistack.admin.modules.system.web.rest.vo.JwtTokenVo;
import org.navistack.framework.captcha.CaptchaTest;
import org.navistack.framework.security.jwt.JwtTokenService;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "System")
public class AuthController {
    private final JwtTokenService jwtTOkenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(JwtTokenService jwtTOkenService,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtTOkenService = jwtTOkenService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    @Operation(summary = "Login by password")
    @Tag(name = "System")
    @CaptchaTest
    public RestResult<JwtTokenVo, ?> login(@Valid PasswordLoginVm vm) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                vm.getLoginName(),
                vm.getPassword()
        );
        authentication = authenticationManagerBuilder.getObject().authenticate(authentication);
        String token = jwtTOkenService.issue(authentication);
        return RestResult.ok(JwtTokenVo.of(token));
    }
}
