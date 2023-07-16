package org.navistack.admin.modules.system.web.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class JwtTokenVo {
    private String idToken;
}
