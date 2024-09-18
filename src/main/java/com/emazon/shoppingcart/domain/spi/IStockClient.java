package com.emazon.shoppingcart.domain.spi;

import com.emazon.shoppingcart.domain.model.ItemShoppingCart;

import java.math.BigInteger;
import java.util.List;

public interface IStockClient {

    BigInteger getQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart);
    List<Long> getCategoriesOfArticle(Long idArticle);

}
