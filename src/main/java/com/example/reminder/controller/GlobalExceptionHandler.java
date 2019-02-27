package com.example.reminder.controller;

import java.util.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(Exception.class)
  public String handleException(Exception e) {
    String stacktrace =  ExceptionUtils.getStackTrace(e);
    log.severe(stacktrace);
    return stacktrace;
  }

}