package com.emazon.shoppingcart.adapters.driving.http.mapper.request;

import com.emazon.shoppingcart.adapters.driving.http.dto.request.ItemShoppingCartRequest;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IShoppingCartRequestMapper {

    @Mapping(target = "id", ignore = true)
    ItemShoppingCart toItemShoppingCart(ItemShoppingCartRequest request);

}
