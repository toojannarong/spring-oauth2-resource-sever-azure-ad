package com.learn.oauth2.resourceserver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @PreAuthorize("hasAnyApplicationRole('Consume.MassLoad', 'Read')")
    @GetMapping("/whoami")
    public Object oauthUserInfo(@AuthenticationPrincipal JwtAuthenticationToken principal) {
        return principal;
    }

}