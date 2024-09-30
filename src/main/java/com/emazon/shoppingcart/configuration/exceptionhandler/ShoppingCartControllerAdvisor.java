package com.emazon.shoppingcart.configuration.exceptionhandler;

import com.emazon.shoppingcart.domain.exception.*;
import com.emazon.shoppingcart.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class ShoppingCartControllerAdvisor {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(ShoppinCartMaximumArticlesByCategoryException.class)
    public ResponseEntity<ExceptionResponse> handleShoppinCartMaximumArticlesByCategoryException(ShoppinCartMaximumArticlesByCategoryException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.EXCEPTION_SHOPPING_CART_MAXIMUM_ARTICLES_BY_CATEGORY, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(ShoppingCartQuantityNotZeroException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingCartQuantityNotZeroException(ShoppingCartQuantityNotZeroException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.EXCEPTION_SHOPPING_CART_QUANTITY_ZERO, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(ShoppingCartUnitsNotAvalaibleException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingCartUnitsNotAvalaibleException(ShoppingCartUnitsNotAvalaibleException ex) {
        String message;
        if(ex.getMessage() != null){
            message = Constants.EXCEPTION_SHOPPING_CART_UNITS_ZERO + LocalDate.parse(ex.getMessage()).plusDays(Constants.DAYS_OF_NEXT_SUPPLY);
        }else{
            message = Constants.EXCEPTION_SHOPPING_CART_UNITS_NOT_AVALAIBLE;
        }
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(message, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(ShoppingCartNoArticlesFoundException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingCartNoArticlesFoundException(ShoppingCartNoArticlesFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(Constants.EXCEPTION_SHOPPING_CART_ARTICLES_NOT_FOUND, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(ArticleInsufficientStockToPurchaseException.class)
    public ResponseEntity<ExceptionResponse> handleArticleInsufficientStockToPurchaseException(ArticleInsufficientStockToPurchaseException ex) throws JsonProcessingException {
        return ResponseEntity.badRequest().body(
                objectMapper.readValue(ex.getMessage(), ExceptionResponse.class)
        );
    }

}
