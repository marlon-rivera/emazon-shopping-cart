package com.emazon.shoppingcart.adapters.driven.jpa.mysql.adapter;

import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ItemShoppingCartEntity;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ShoppingCartEntity;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.mapper.IShoppingCartEntityMapper;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.repository.IShoppingCartRepository;
import com.emazon.shoppingcart.domain.model.ShoppingCart;
import com.emazon.shoppingcart.domain.spi.IShoppingCartPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ShoppingCartAdapter implements IShoppingCartPersistencePort {

    private final IShoppingCartRepository shoppingCartRepository;
    private final IShoppingCartEntityMapper shoppingCartEntityMapper;

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart);
        List<ItemShoppingCartEntity> itemShoppingCartEntities = shoppingCartEntity.getItems();
        for (ItemShoppingCartEntity itemShoppingCartEntity : itemShoppingCartEntities) {
            if(itemShoppingCartEntity.getShoppingCart() == null) {
                itemShoppingCartEntity.setShoppingCart(shoppingCartEntity);
            }
        }
        shoppingCartRepository.save(shoppingCartEntity);
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartByIdClient(String idClient) {
        return shoppingCartEntityMapper.toOptionalShoppingCart(shoppingCartRepository.findShoppingCartEntityByIdClient(idClient));
    }
}
