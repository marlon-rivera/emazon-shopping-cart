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
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ShoppingCartUseCaseImpl implements IShoppingCartServicePort {

    private final IShoppingCartPersistencePort shoppingCartPersistencePort;
    private final IAuthenticationPort authenticationPort;
    private final IStockClient stockClient;
    private final ISupplyClient supplyClient;

    @Override
    public void addItemShoppingCart(ItemShoppingCart itemShoppingCart) {
        verifyQuantityItemShoppingCart(itemShoppingCart);
        String idClient = authenticationPort.getCurrentUsername();
        ShoppingCart shoppingCart = getShoppingCartByIdClient(idClient);

        Optional<ItemShoppingCart> existingItem = findItemShoppingCart(shoppingCart, itemShoppingCart.getIdArticle());

        if (existingItem.isPresent()) {
            updateExistingItem(existingItem.get(), itemShoppingCart, shoppingCart);
        } else {
            validateCategoriesItems(shoppingCart.getItems(), itemShoppingCart);
            shoppingCart.addItem(itemShoppingCart);
        }

        updateShoppingCart(shoppingCart);
    }

    @Override
    public void removeItemShoppingCart(Long idArticle) {
        String idClient = authenticationPort.getCurrentUsername();
        ShoppingCart shoppingCart = getShoppingCartByIdClient(idClient);

        if (!shoppingCart.getItems().isEmpty()) {
            removeItemFromCart(idArticle, shoppingCart);
        }
    }

    private void removeItemFromCart(Long idArticle, ShoppingCart shoppingCart) {
        Optional<ItemShoppingCart> itemShoppingCartOptional = findItemShoppingCart(shoppingCart, idArticle);
        itemShoppingCartOptional.ifPresent(item -> {
            shoppingCart.getItems().remove(item);
            updateShoppingCart(shoppingCart);
        });
    }

    private void updateExistingItem(ItemShoppingCart existingItem, ItemShoppingCart newItem, ShoppingCart shoppingCart) {
        if (!existingItem.getQuantity().equals(newItem.getQuantity())) {
            validateCategoriesItems(shoppingCart.getItems(), newItem);
            existingItem.setQuantity(newItem.getQuantity());
        }
    }

    private void updateShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setModificationDate(LocalDate.now());
        shoppingCartPersistencePort.saveShoppingCart(shoppingCart);
    }

    private ShoppingCart getShoppingCartByIdClient(String idClient) {
        return shoppingCartPersistencePort
                .getShoppingCartByIdClient(idClient)
                .orElseGet(() -> new ShoppingCart(null, idClient));
    }

    private Optional<ItemShoppingCart> findItemShoppingCart(ShoppingCart shoppingCart, Long idArticle) {
        return shoppingCart.getItems().stream()
                .filter(item -> item.getIdArticle().equals(idArticle))
                .findFirst();
    }

    private void verifyQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart) {
        if (itemShoppingCart.getQuantity().compareTo(BigInteger.ZERO) <= 0) {
            throw new ShoppingCartQuantityNotZeroException();
        }
        BigInteger quantityAvailable = stockClient.getQuantityItemShoppingCart(itemShoppingCart);
        if (quantityAvailable.compareTo(BigInteger.ZERO) <= 0) {
            LocalDate lastDeliveryDate = getLastDeliveryDateofArticle(itemShoppingCart.getIdArticle());
            throw new ShoppingCartUnitsNotAvalaibleException(
                    lastDeliveryDate == null ? LocalDate.now().toString() : lastDeliveryDate.toString()
            );
        }
        if (quantityAvailable.compareTo(itemShoppingCart.getQuantity()) < 0) {
            throw new ShoppingCartUnitsNotAvalaibleException();
        }
    }

    private void validateCategoriesItems(List<ItemShoppingCart> items, ItemShoppingCart itemShoppingCart) {
        List<Long> categoriesItemAdd = stockClient.getCategoriesOfArticle(itemShoppingCart.getIdArticle());

        for (Long categoryId : categoriesItemAdd) {
            if (countItemsByCategory(items, categoryId) >= Constants.MAXIMUM_ARTICLES_BY_CATEGORY) {
                throw new ShoppinCartMaximumArticlesByCategoryException();
            }
        }
    }

    private int countItemsByCategory(List<ItemShoppingCart> items, Long categoryId) {
        return (int) items.stream()
                .filter(item -> stockClient.getCategoriesOfArticle(item.getIdArticle()).contains(categoryId))
                .count();
    }

    private LocalDate getLastDeliveryDateofArticle(Long idArticle) {
        return supplyClient.getLastDeliveryDateofArticle(idArticle);
    }
}
