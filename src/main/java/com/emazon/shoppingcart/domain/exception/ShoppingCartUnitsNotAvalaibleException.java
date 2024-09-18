package com.emazon.shoppingcart.domain.exception;

public class ShoppingCartUnitsNotAvalaibleException extends RuntimeException {
    public ShoppingCartUnitsNotAvalaibleException(String date) {
        super(date);
    }

    public ShoppingCartUnitsNotAvalaibleException() {
        super();
    }
}
