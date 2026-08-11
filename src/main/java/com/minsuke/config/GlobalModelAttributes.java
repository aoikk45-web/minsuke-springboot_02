package com.minsuke.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.minsuke.auth.security.MinsukeUserDetails;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("currentUser")
    public MinsukeUserDetails currentUser(@AuthenticationPrincipal MinsukeUserDetails userDetails) {
        return userDetails;
    }
}
