package com.codecampx.codecampx.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String extractUserName(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
