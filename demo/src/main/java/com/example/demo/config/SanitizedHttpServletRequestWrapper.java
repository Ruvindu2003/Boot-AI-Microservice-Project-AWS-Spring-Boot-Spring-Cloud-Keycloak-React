package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class SanitizedHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String sanitizedUri;

    public SanitizedHttpServletRequestWrapper(HttpServletRequest request, String sanitizedUri) {
        super(request);
        this.sanitizedUri = sanitizedUri;
    }

    @Override
    public String getRequestURI() {
        return sanitizedUri;
    }

    @Override
    public String getServletPath() {
        // naive: return sanitizedUri — this works for simple controllers
        return sanitizedUri;
    }
}
