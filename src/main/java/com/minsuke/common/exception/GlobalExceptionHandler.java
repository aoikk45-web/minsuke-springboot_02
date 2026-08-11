package com.minsuke.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.minsuke.event.exception.EventAccessDeniedException;
import com.minsuke.event.exception.EventNotFoundException;
import com.minsuke.family.exception.FamilyAccessDeniedException;
import com.minsuke.family.exception.FamilyNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FamilyAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleFamilyAccessDenied(FamilyAccessDeniedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/forbidden";
    }

    @ExceptionHandler(FamilyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFamilyNotFound(FamilyNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(EventAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleEventAccessDenied(EventAccessDeniedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/forbidden";
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEventNotFound(EventNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpectedException(Exception ex, Model model) {
        log.error("Unhandled exception", ex);
        model.addAttribute("message", "予期しないエラーが発生しました。");
        return "error";
    }
}
