package com.personalweb.website.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class SessionHelper implements Serializable {

    public static Long getUserId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("userId");
    }
}
