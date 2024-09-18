package com.emazon.shoppingcart.configuration.exceptionhandler;

import com.emazon.shoppingcart.utils.Constants;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.LocalDateTime;

@ControllerAdvice
public class FeignControllerAdvisor {

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionResponse> handleConnectException(ConnectException ex) {
        return ResponseEntity.internalServerError().body(
                new ExceptionResponse(Constants.EXCEPTION_SHOPPING_CART_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(FeignException.NotFound ex) {
        return new ResponseEntity<>(
                    new ExceptionResponse(Constants.EXCEPTION_SHOPPING_CART_ARTICLE_NOT_FOUND, HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()), HttpStatus.NOT_FOUND
        );

    }

}
