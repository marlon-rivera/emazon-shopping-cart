package com.emazon.shoppingcart.domain.api;

import com.emazon.shoppingcart.domain.model.Article;
import com.emazon.shoppingcart.domain.model.ArticlesShoppingCart;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.PaginationInfo;

import java.util.List;

public interface IShoppingCartServicePort {

    void addItemShoppingCart(ItemShoppingCart itemShoppingCart);
    void removeItemShoppingCart(Long idArticle);
    ArticlesShoppingCart getArticlesOfShoppingCart(int page, int size, String order, List<Long> idsCategories, List<Long> idsBrands);
}
