package com.exception;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Global exception handler for application.
 */
@RestControllerAdvice
@PropertySource("classpath:error-messages.properties")
public class GlobalExceptionHandler {

  @Value("${error.message.bad_request}")
  private String badRequestMessage;

  @Value("${not.found.error.message}")
  private String notFoundErrorMessage;

  @Value("${internal.server.error.message}")
  private String internalServerErrorMessage;


  /**
   * Method that catches all exceptions which not captured.
   * Handle:
   *
   * @param ex - exception that was thrown.
   * @return ResponseEntity containing the {@link String}  and status 404.
   * @see Exception
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> globalException(Exception ex) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalServerErrorMessage);
  }


  /**
   * Method that catches illegal argument in field.
   * Handle:
   *
   * @param ex - exception that was thrown.
   * @return ResponseEntity containing the {@link String} and status 400 .
   * @see IllegalArgumentException
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestMessage);
  }


  /**
   * Method that catches when no element as a result of searching.
   * Handle:
   *
   * @param ex - exception that was thrown.
   * @return ResponseEntity containing the {@link String} and status 404.
   * @see NoSuchElementException
   */
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundErrorMessage);
  }
}
