package com.emazon.shoppingcart.domain.spi;

import com.emazon.shoppingcart.domain.model.Article;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.PaginationInfo;
import com.emazon.shoppingcart.domain.model.PurchaseRequest;

import java.math.BigInteger;
import java.util.List;

public interface IStockClient {

    BigInteger getQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart);
    List<Long> getCategoriesOfArticle(Long idArticle);
    PaginationInfo<Article> getArticlesOfShoppingCart(int page, int size, List<Long> idsArticles, String order, List<Long> idsCategories, List<Long> idsBrands);
    void purchase(PurchaseRequest purchaseRequest);

}
