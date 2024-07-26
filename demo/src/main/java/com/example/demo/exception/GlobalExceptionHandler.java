package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, WebRequest request, Model model) {
        model.addAttribute("timestamp", new Date());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", ex.getErrorCode());
        model.addAttribute("details", request.getDescription(false));
        return "error/custom-error"; // Return the view name for custom error
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, WebRequest request, Model model) {
        model.addAttribute("timestamp", new Date());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "INTERNAL_SERVER_ERROR");
        model.addAttribute("details", request.getDescription(false));
        return "error/global-error"; // Return the view name for global error
    }
}
