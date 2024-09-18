package com.emazon.shoppingcart.domain.api.usecase;

import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
import com.emazon.shoppingcart.domain.exception.ShoppinCartMaximumArticlesByCategoryException;
import com.emazon.shoppingcart.domain.exception.ShoppingCartQuantityNotZeroException;
import com.emazon.shoppingcart.domain.exception.ShoppingCartUnitsNotAvalaibleException;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.emazon.shoppingcart.domain.model.ShoppingCart;
import com.emazon.shoppingcart.domain.spi.IAuthenticationPort;
import com.emazon.shoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.shoppingcart.domain.spi.IStockClient;
import com.emazon.shoppingcart.domain.spi.ISupplyClient;
import com.emazon.shoppingcart.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShoppingCartUseCaseImplTest {

    @Mock
    private IShoppingCartPersistencePort shoppingCartPersistencePort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @Mock
    private IStockClient stockClient;

    @Mock
    private ISupplyClient supplyClient;

    @InjectMocks
    private ShoppingCartUseCaseImpl shoppingCartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addItemShoppingCart_ShouldAddItem_WhenItemIsNew() {
        ItemShoppingCart item = new ItemShoppingCart(1L, 1L, BigInteger.TEN);

        String clientId = "client1";
        ShoppingCart shoppingCart = new ShoppingCart(1L, clientId);
        shoppingCart.setItems(new ArrayList<>());

        when(authenticationPort.getCurrentUsername()).thenReturn(clientId);
        when(shoppingCartPersistencePort.getShoppingCartByIdClient(clientId)).thenReturn(Optional.of(shoppingCart));
        when(stockClient.getQuantityItemShoppingCart(item)).thenReturn(BigInteger.TEN);

        shoppingCartUseCase.addItemShoppingCart(item);

        verify(shoppingCartPersistencePort).saveShoppingCart(any(ShoppingCart.class));
    }

    @Test
    void addItemShoppingCart_ShouldUpdateQuantity_WhenItemAlreadyExists() {
        ItemShoppingCart item = new ItemShoppingCart(1L, 1L, BigInteger.TEN);
        Long itemId = 1L;

        ItemShoppingCart existingItem = new ItemShoppingCart(itemId, 1L, BigInteger.ONE);

        String clientId = "client1";
        ShoppingCart shoppingCart = new ShoppingCart(1L, clientId);
        shoppingCart.setItems(List.of(existingItem));


        when(authenticationPort.getCurrentUsername()).thenReturn(clientId);
        when(shoppingCartPersistencePort.getShoppingCartByIdClient(clientId)).thenReturn(Optional.of(shoppingCart));
        when(stockClient.getQuantityItemShoppingCart(item)).thenReturn(BigInteger.TEN);

        shoppingCartUseCase.addItemShoppingCart(item);

        assertEquals(item.getQuantity(), existingItem.getQuantity());
        verify(shoppingCartPersistencePort).saveShoppingCart(any(ShoppingCart.class));
    }


}