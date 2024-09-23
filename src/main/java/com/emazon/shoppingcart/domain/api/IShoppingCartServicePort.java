package com.emazon.shoppingcart.domain.api;

import com.emazon.shoppingcart.domain.model.ItemShoppingCart;

import java.time.LocalDate;

public interface IShoppingCartServicePort {

    void addItemShoppingCart(ItemShoppingCart itemShoppingCart);
    void removeItemShoppingCart(Long idArticle);


}
