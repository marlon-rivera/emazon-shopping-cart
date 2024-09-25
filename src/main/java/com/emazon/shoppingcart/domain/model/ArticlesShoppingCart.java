package com.emazon.shoppingcart.domain.model;

import com.emazon.shoppingcart.utils.Constants;

import java.math.BigDecimal;

public class ArticlesShoppingCart {

    private PaginationInfo<Article> articles;
    private BigDecimal totalPrice;

    public ArticlesShoppingCart(PaginationInfo<Article> articles, BigDecimal totalPrice){
        this.articles = articles;
        this.totalPrice = totalPrice;
    }

    public void setArticles(PaginationInfo<Article> articles) {
        this.articles = articles;
    }

    public PaginationInfo<Article> getArticles() {
        return articles;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
