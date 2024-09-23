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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void addItemShoppingCart_shouldThrowExceptionWhenExceedingCategoryLimit() {
        ShoppingCart shoppingCart = new ShoppingCart(1L, "testClient");
        ItemShoppingCart itemShoppingCart = new ItemShoppingCart(1L, 1L, BigInteger.TEN);
        when(authenticationPort.getCurrentUsername()).thenReturn("testClient");
        when(shoppingCartPersistencePort.getShoppingCartByIdClient("testClient")).thenReturn(Optional.of(shoppingCart));
        when(stockClient.getQuantityItemShoppingCart(itemShoppingCart)).thenReturn(BigInteger.valueOf(20));

        List<Long> categoriesItemToAdd = List.of(1L);
        when(stockClient.getCategoriesOfArticle(itemShoppingCart.getIdArticle())).thenReturn(categoriesItemToAdd);

        shoppingCart.addItem(new ItemShoppingCart(2L, 2L, BigInteger.valueOf(5)));
        shoppingCart.addItem(new ItemShoppingCart(3L, 3L, BigInteger.valueOf(3)));
        shoppingCart.addItem(new ItemShoppingCart(4L, 4L, BigInteger.valueOf(1)));

        List<Long> categoriesExistingItem1 = List.of(1L);
        List<Long> categoriesExistingItem2 = List.of(1L);
        List<Long> categoriesExistingItem3 = List.of(1L);

        when(stockClient.getCategoriesOfArticle(2L)).thenReturn(categoriesExistingItem1);
        when(stockClient.getCategoriesOfArticle(3L)).thenReturn(categoriesExistingItem2);
        when(stockClient.getCategoriesOfArticle(4L)).thenReturn(categoriesExistingItem3);

        assertThrows(ShoppinCartMaximumArticlesByCategoryException.class, () -> {
            shoppingCartUseCase.addItemShoppingCart(itemShoppingCart);
        });

        verify(shoppingCartPersistencePort, never()).saveShoppingCart(any(ShoppingCart.class));
    }

    @Test
    void addItemShoppingCart_shouldAddWhenNotExceedingCategoryLimit() {
        ShoppingCart shoppingCart = new ShoppingCart(1L, "testClient");
        ItemShoppingCart itemShoppingCart = new ItemShoppingCart(1L, 1L, BigInteger.TEN);
        when(authenticationPort.getCurrentUsername()).thenReturn("testClient");
        when(shoppingCartPersistencePort.getShoppingCartByIdClient("testClient")).thenReturn(Optional.of(shoppingCart));
        when(stockClient.getQuantityItemShoppingCart(itemShoppingCart)).thenReturn(BigInteger.valueOf(20));

        List<Long> categoriesItemToAdd = List.of(1L);
        when(stockClient.getCategoriesOfArticle(itemShoppingCart.getIdArticle())).thenReturn(categoriesItemToAdd);

        shoppingCart.addItem(new ItemShoppingCart(2L, 2L, BigInteger.valueOf(5)));
        shoppingCart.addItem(new ItemShoppingCart(3L, 2L, BigInteger.valueOf(3)));

        List<Long> categoriesExistingItem1 = List.of(1L);
        List<Long> categoriesExistingItem2 = List.of(1L);

        when(stockClient.getCategoriesOfArticle(2L)).thenReturn(categoriesExistingItem1);
        when(stockClient.getCategoriesOfArticle(3L)).thenReturn(categoriesExistingItem2);

        shoppingCartUseCase.addItemShoppingCart(itemShoppingCart);

        verify(shoppingCartPersistencePort).saveShoppingCart(shoppingCart);
        
        assertTrue(shoppingCart.getItems().contains(itemShoppingCart));
    }

    @Test
    void removeItemShoppingCart_shouldRemoveItemAndValidate() {
        Long idArticle = 1L;
        String idClient = "client123";

        ItemShoppingCart itemShoppingCart = new ItemShoppingCart(idArticle, 1L, BigInteger.TEN);
        List<ItemShoppingCart> items = new ArrayList<>();
        items.add(itemShoppingCart);
        ShoppingCart shoppingCart = new ShoppingCart(1L, idClient);
        shoppingCart.setItems(items);

        when(authenticationPort.getCurrentUsername()).thenReturn(idClient);
        when(shoppingCartPersistencePort.getShoppingCartByIdClient(idClient)).thenReturn(Optional.of(shoppingCart));
        when(stockClient.getQuantityItemShoppingCart(itemShoppingCart)).thenReturn(BigInteger.TEN);

        shoppingCartUseCase.removeItemShoppingCart(idArticle);

        assertFalse(shoppingCart.getItems().contains(itemShoppingCart));
        verify(shoppingCartPersistencePort).removeItemShoppingCart(shoppingCart);
        assertEquals(shoppingCart.getModificationDate(), LocalDate.now());
    }


    @Test
    void removeItemShoppingCart_shouldNotRemoveItemWhenNotPresent() {
        Long idArticle = 1L;
        String idClient = "client123";

        ShoppingCart shoppingCart = new ShoppingCart(1L, idClient);

        when(authenticationPort.getCurrentUsername()).thenReturn(idClient);
        when(shoppingCartPersistencePort.getShoppingCartByIdClient(idClient)).thenReturn(Optional.of(shoppingCart));

        shoppingCartUseCase.removeItemShoppingCart(idArticle);

        verify(shoppingCartPersistencePort, never()).removeItemShoppingCart(any());
        assertTrue(shoppingCart.getItems().isEmpty());
    }
}