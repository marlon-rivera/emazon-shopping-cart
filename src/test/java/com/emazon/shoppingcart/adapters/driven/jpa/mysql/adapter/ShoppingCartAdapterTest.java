package com.emazon.shoppingcart.adapters.driven.jpa.mysql.adapter;


import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ItemShoppingCartEntity;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.entity.ShoppingCartEntity;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.mapper.IShoppingCartEntityMapper;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.repository.IShoppingCartRepository;
import com.emazon.shoppingcart.domain.model.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartAdapterTest {

    @Mock
    private IShoppingCartRepository shoppingCartRepository;

    @Mock
    private IShoppingCartEntityMapper shoppingCartEntityMapper;

    @InjectMocks
    private ShoppingCartAdapter shoppingCartAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveShoppingCart_ShouldSaveShoppingCart_WhenCalled() {
        ShoppingCart shoppingCart = new ShoppingCart(1L, "123");
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(1L);
        shoppingCartEntity.setIdClient("123");
        when(shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart)).thenReturn(shoppingCartEntity);

        shoppingCartAdapter.saveShoppingCart(shoppingCart);

        verify(shoppingCartEntityMapper).toShoppingCartEntity(shoppingCart);
        verify(shoppingCartRepository).save(shoppingCartEntity);
    }

    @Test
    void saveShoppingCart_ShouldSetShoppingCartInItems_WhenItemShoppingCartEntityHasNullShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart(1L, "123");
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        ItemShoppingCartEntity item = new ItemShoppingCartEntity();
        item.setShoppingCart(null);
        shoppingCartEntity.setItems(Collections.singletonList(item));
        when(shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart)).thenReturn(shoppingCartEntity);

        shoppingCartAdapter.saveShoppingCart(shoppingCart);

        assertNotNull(item.getShoppingCart());
        verify(shoppingCartRepository).save(shoppingCartEntity);
    }

    @Test
    void getShoppingCartByIdClient_ShouldReturnShoppingCart_WhenFound() {
        String idClient = "client1";
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setIdClient(idClient);
        shoppingCartEntity.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart(1L, idClient);
        when(shoppingCartRepository.findShoppingCartEntityByIdClient(idClient)).thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartEntityMapper.toOptionalShoppingCart(Optional.of(shoppingCartEntity))).thenReturn(Optional.of(shoppingCart));

        Optional<ShoppingCart> result = shoppingCartAdapter.getShoppingCartByIdClient(idClient);

        assertTrue(result.isPresent());
        assertEquals(shoppingCart, result.get());
    }

    @Test
    void getShoppingCartByIdClient_ShouldReturnEmpty_WhenNotFound() {
        String idClient = "client2";
        when(shoppingCartRepository.findShoppingCartEntityByIdClient(idClient)).thenReturn(Optional.empty());

        Optional<ShoppingCart> result = shoppingCartAdapter.getShoppingCartByIdClient(idClient);

        assertFalse(result.isPresent());
    }

    @Test
    void removeItemShoppingCart_shouldSaveUpdatedShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart(1L, "client123");
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();


        when(shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart)).thenReturn(shoppingCartEntity);

        shoppingCartAdapter.removeItemShoppingCart(shoppingCart);

        verify(shoppingCartRepository).save(shoppingCartEntity);
    }
}