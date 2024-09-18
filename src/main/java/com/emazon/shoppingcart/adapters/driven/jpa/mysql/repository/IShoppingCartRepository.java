package com.emazon.shoppingcart.adapters.driven.jpa.mysql.repository;

import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IShoppingCartRepository  extends JpaRepository<ShoppingCartEntity, Long> {

    Optional<ShoppingCartEntity> findShoppingCartEntityByIdClient(String idClient);

}
