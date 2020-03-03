package com.learn.oauth2.resourceserver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @PreAuthorize("hasAuthority('Read')")
        @GetMapping("/whoami")
        public Object whoami() {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
}