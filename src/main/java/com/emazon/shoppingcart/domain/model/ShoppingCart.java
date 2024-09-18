package com.emazon.shoppingcart.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private Long id;
    private String idClient;
    private List<ItemShoppingCart> items = new ArrayList<>();
    private LocalDate modificationDate;

    public ShoppingCart(Long id, String idClient) {
        this.id = id;
        this.idClient = idClient;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void addItem(ItemShoppingCart item) {
        items.add(item);
    }

    public void removeItem(ItemShoppingCart item) {
        items.remove(item);
    }

    public List<ItemShoppingCart> getItems() {
        return items;
    }

    public void setItems(List<ItemShoppingCart> items) {
        this.items = items;
    }
}
