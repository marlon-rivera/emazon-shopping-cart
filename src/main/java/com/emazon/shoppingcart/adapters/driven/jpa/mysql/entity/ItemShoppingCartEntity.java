package com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity(name = "item_shopping_cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long idArticle;
    @Column(nullable = false)
    private BigInteger quantity;
    @ManyToOne
    @JoinColumn(name = "id_shopping_cart")
    private ShoppingCartEntity shoppingCart;

}
