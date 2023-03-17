package com.jeeplus.qc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {
    public CookieUtils() {
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 86400);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String path) {
        setCookie(response, name, value, path, 86400);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, (String)null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        try {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        }

        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, (HttpServletResponse)null, name, false);
    }

    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }

    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie[] var9 = cookies;
            int var8 = cookies.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                Cookie cookie = var9[var7];
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException var11) {
                        var11.printStackTrace();
                    }

                    if (isRemove) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }

        return value;
    }
}
