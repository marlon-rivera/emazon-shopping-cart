package com.emazon.shoppingcart.domain.model;

import java.math.BigInteger;

public class ItemShoppingCart {

    private Long id;
    private Long idArticle;
    private BigInteger quantity;

    public ItemShoppingCart(Long id, Long idArticle, BigInteger quantity) {
        this.id = id;
        this.idArticle = idArticle;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

}
