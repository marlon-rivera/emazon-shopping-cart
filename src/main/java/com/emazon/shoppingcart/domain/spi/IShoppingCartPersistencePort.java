package com.emazon.shoppingcart.domain.spi;

import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.ShoppingCart;

import java.time.LocalDate;
import java.util.Optional;

public interface IShoppingCartPersistencePort {

    void saveShoppingCart(ShoppingCart shoppingCart);
    Optional<ShoppingCart> getShoppingCartByIdClient(String idClient);
    void removeItemShoppingCart(ShoppingCart shoppingCart);
}
