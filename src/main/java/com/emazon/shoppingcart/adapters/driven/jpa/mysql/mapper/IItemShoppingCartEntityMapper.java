package com.emazon.shoppingcart.adapters.driven.jpa.mysql.mapper;

import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ItemShoppingCartEntity;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ShoppingCartEntity;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IItemShoppingCartEntityMapper {

    ItemShoppingCart toItemShoppingCart(ItemShoppingCartEntity itemShoppingCartEntity);
    ItemShoppingCartEntity toItemShoppingCartEntity(ItemShoppingCart itemShoppingCart);
    default Optional<ItemShoppingCart> toOptionalItemShoppingCart(Optional<ItemShoppingCartEntity> itemShoppingCartEntityOptional) {
        return itemShoppingCartEntityOptional.map(this::toItemShoppingCart);
    }

}
