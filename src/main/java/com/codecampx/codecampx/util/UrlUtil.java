package com.codecampx.codecampx.util;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
