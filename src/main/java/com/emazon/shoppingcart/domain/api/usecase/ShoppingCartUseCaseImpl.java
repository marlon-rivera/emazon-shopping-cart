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
        Optional<ItemShoppingCart> existingItem = findItemShoppingCart(shoppingCart, itemShoppingCart);
        if(existingItem.isPresent()) {
            if(existingItem.get().getQuantity().equals(itemShoppingCart.getQuantity())) {
                return;
            }
            validateCategoriesItems(shoppingCart.getItems(), itemShoppingCart);
            existingItem.get().setQuantity(itemShoppingCart.getQuantity());
        }else{
            validateCategoriesItems(shoppingCart.getItems(), itemShoppingCart);
            shoppingCart.addItem(itemShoppingCart);
        }
        shoppingCart.setModificationDate(LocalDate.now());
        shoppingCartPersistencePort.saveShoppingCart(shoppingCart);
    }

    private ShoppingCart getShoppingCartByIdClient(String idClient) {
        return shoppingCartPersistencePort
                .getShoppingCartByIdClient(idClient)
                .orElseGet(() -> new ShoppingCart(null, idClient));
    }

    private Optional<ItemShoppingCart> findItemShoppingCart(ShoppingCart shoppingCart, ItemShoppingCart itemShoppingCart) {
        return shoppingCart.getItems().stream()
                .filter(item -> item.getIdArticle().equals(itemShoppingCart.getIdArticle()))
                .findFirst();
    }

    private void verifyQuantityItemShoppingCart(ItemShoppingCart itemShoppingCart) {
        if(itemShoppingCart.getQuantity().compareTo(BigInteger.ZERO) == BigInteger.ZERO.intValue()){
            throw new ShoppingCartQuantityNotZeroException();
        }
        BigInteger quantityAvalaible = stockClient.getQuantityItemShoppingCart(itemShoppingCart);
        if(quantityAvalaible.compareTo(BigInteger.ZERO) == BigInteger.ZERO.intValue()){
            LocalDate lastDeliveryDate = getLastDeliveryDateofArticle(itemShoppingCart.getIdArticle());
            throw new ShoppingCartUnitsNotAvalaibleException(lastDeliveryDate == null ? LocalDate.now().toString() : lastDeliveryDate.toString());
        }
        if(quantityAvalaible.compareTo(itemShoppingCart.getQuantity()) < BigInteger.ZERO.intValue()) {
            throw new ShoppingCartUnitsNotAvalaibleException();
        }
    }

    private void validateCategoriesItems(List<ItemShoppingCart> items, ItemShoppingCart itemShoppingCart) {
        List<Long> categoriesItemAdd = stockClient.getCategoriesOfArticle(itemShoppingCart.getIdArticle());
        for (Long categoryId: categoriesItemAdd) {
            int countCateogry = 0;
            for (ItemShoppingCart item : items) {
                List<Long> idsCategories = stockClient.getCategoriesOfArticle(item.getIdArticle());
                if(idsCategories.contains(categoryId)) {
                    countCateogry++;
                }
            }
            if(countCateogry == Constants.MAXIMUM_ARTICLES_BY_CATEGORY){
                throw new ShoppinCartMaximumArticlesByCategoryException();
            }

        }
    }

    private LocalDate getLastDeliveryDateofArticle(Long idArticle){
        return supplyClient.getLastDeliveryDateofArticle(idArticle);
    }
}
