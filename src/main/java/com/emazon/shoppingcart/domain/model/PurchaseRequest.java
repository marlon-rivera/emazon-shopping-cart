package com.emazon.shoppingcart.domain.model;

import java.math.BigInteger;
import java.util.List;

public class PurchaseRequest {

    private List<Long> idsArticles;
    private List<BigInteger> quantities;

    public PurchaseRequest(List<Long> idsArticles, List<BigInteger> quantities) {
        this.idsArticles = idsArticles;
        this.quantities = quantities;
    }

    public List<BigInteger> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<BigInteger> quantities) {
        this.quantities = quantities;
    }

    public List<Long> getIdsArticles() {
        return idsArticles;
    }

    public void setIdsArticles(List<Long> idsArticles) {
        this.idsArticles = idsArticles;
    }
}
