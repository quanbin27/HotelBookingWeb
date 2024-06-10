package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final List<String> STATIC_RESOURCES_EXTENSIONS = Arrays.asList(
            "css", "js", "png", "jpg", "jpeg", "gif", "woff", "ttf", "svg" ,"woff2"
    );

    private boolean isStaticResource(String requestURI) {
        String extension = requestURI.substring(requestURI.lastIndexOf('.') + 1);
        return STATIC_RESOURCES_EXTENSIONS.contains(extension);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (!isStaticResource(requestURI)) {
            System.out.println("Pre Handle method - Before handling the request: " + requestURI);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        if (!isStaticResource(requestURI)) {
            System.out.println("Post Handle method - After handling the request: " + requestURI);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String requestURI = request.getRequestURI();
        if (!isStaticResource(requestURI)) {
            System.out.println("Request and Response is completed: " + requestURI);
        }
    }
}
