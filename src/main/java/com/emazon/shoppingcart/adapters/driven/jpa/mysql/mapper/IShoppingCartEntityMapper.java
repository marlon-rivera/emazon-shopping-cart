package com.emazon.shoppingcart.adapters.driven.jpa.mysql.mapper;

import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ShoppingCartEntity;
import com.emazon.shoppingcart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IShoppingCartEntityMapper {

    ShoppingCart toShoppingCart(ShoppingCartEntity shoppingCartEntity);
    ShoppingCartEntity toShoppingCartEntity(ShoppingCart shoppingCart);
    default Optional<ShoppingCart> toOptionalShoppingCart(Optional<ShoppingCartEntity> shoppingCartEntityOptional) {
        return shoppingCartEntityOptional.map(this::toShoppingCart);
    }

}
