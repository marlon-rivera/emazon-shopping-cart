package com.emazon.shoppingcart.adapters.driven.feign;

import com.emazon.shoppingcart.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.emazon.shoppingcart.domain.exception.ArticleInsufficientStockToPurchaseException;
import com.emazon.shoppingcart.domain.model.Article;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.PaginationInfo;
import com.emazon.shoppingcart.domain.model.PurchaseRequest;
import com.emazon.shoppingcart.domain.spi.IStockClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
public class StockFeignAdapter implements IStockClient {

    private final IStockFeignClient stockFeignClient;
    private final IArticleResponseMapper articleResponseMapper;

    @Override
    public BigInteger getQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart) {
        return stockFeignClient.getQuantityOfArticle(itemShoppingCart.getIdArticle());
    }

    @Override
    public List<Long> getCategoriesOfArticle(Long idArticle) {
        return stockFeignClient.getCategoriesOfArticle(idArticle);
    }

    @Override
    public PaginationInfo<Article> getArticlesOfShoppingCart(int page, int size, List<Long> idsArticles, String order, List<Long> idsCategories, List<Long> idsBrands) {
        return articleResponseMapper.toPaginationInfo(stockFeignClient.getArticlesOfShoppingCart(page, size, idsArticles, order, idsCategories, idsBrands));
    }

    @Override
    public void purchase(PurchaseRequest purchaseRequest) {
        try{
            stockFeignClient.purchase(purchaseRequest);
        }catch (FeignException.BadRequest ex){
            throw new ArticleInsufficientStockToPurchaseException(ex.contentUTF8());
        }

    }
}
