package com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String idClient;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoppingCart", orphanRemoval = true)
    private List<ItemShoppingCartEntity> items = new ArrayList<>();
    @Column(nullable = false)
    private LocalDate modificationDate;

    public void addItemShoppingCart(ItemShoppingCartEntity itemShoppingCart) {
        items.add(itemShoppingCart);
        itemShoppingCart.setShoppingCart(this);
    }

}
