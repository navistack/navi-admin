package org.navistack.admin.modules.system.web.rest;

import org.navistack.framework.captcha.simplecaptcha.SimpleCaptchaService;
import org.navistack.framework.captcha.simplecaptcha.UserAttemptResult;
import org.navistack.framework.web.rest.RestResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/sys/simple-captcha")
@ConditionalOnBean(SimpleCaptchaService.class)
public class SysSimpleCaptchaController {

    private final SimpleCaptchaService simpleCaptchaService;

    public SysSimpleCaptchaController(SimpleCaptchaService simpleCaptchaService) {
        this.simpleCaptchaService = simpleCaptchaService;
    }

    @PostMapping("/challenge")
    public RestResult<String, ?> challenge() {
        return RestResult.ok(simpleCaptchaService.challenge());
    }

    @PostMapping("/answer")
    public RestResult<UserAttemptResult, ?> answer(String challengeId, String answer) {
        return RestResult.ok(simpleCaptchaService.answer(challengeId, answer));
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> image(String challenge) throws Exception {
        RenderedImage image = simpleCaptchaService.draw(challenge);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
