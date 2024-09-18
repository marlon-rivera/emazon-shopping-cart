package com.emazon.shoppingcart.adapters.driven.feign;

import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.spi.IStockClient;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
public class StockFeignAdapter implements IStockClient {

    private final IStockFeignClient stockFeignClient;

    @Override
    public BigInteger getQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart) {
        return stockFeignClient.getQuantityOfArticle(itemShoppingCart.getIdArticle());
    }

    @Override
    public List<Long> getCategoriesOfArticle(Long idArticle) {
        return stockFeignClient.getCategoriesOfArticle(idArticle);
    }
}
