package org.navistack.admin.modules.system.web.rest;

import org.navistack.framework.captcha.simplecaptcha.AbstractSimpleCaptchaController;
import org.navistack.framework.captcha.simplecaptcha.SimpleCaptchaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/simple-captcha")
@ConditionalOnBean(SimpleCaptchaService.class)
public class SysSimpleCaptchaController extends AbstractSimpleCaptchaController {
    public SysSimpleCaptchaController(SimpleCaptchaService simpleCaptchaService) {
        super(simpleCaptchaService);
    }
}
