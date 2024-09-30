package com.emazon.shoppingcart.domain.exception;

public class ArticleInsufficientStockToPurchaseException extends RuntimeException {
    public ArticleInsufficientStockToPurchaseException(String message) {
        super(message);
    }
}
