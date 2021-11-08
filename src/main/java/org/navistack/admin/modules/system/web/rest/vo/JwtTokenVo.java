package org.navistack.admin.modules.system.web.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenVo {
    private String idToken;

    public static JwtTokenVo of(String idToken) {
        return new JwtTokenVo(idToken);
    }
}
