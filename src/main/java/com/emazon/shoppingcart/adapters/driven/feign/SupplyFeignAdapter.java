package com.emazon.shoppingcart.adapters.driven.feign;

import com.emazon.shoppingcart.domain.spi.ISupplyClient;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SupplyFeignAdapter implements ISupplyClient {

    private final ISupplyFeignClient supplyFeignClient;

    @Override
    public LocalDate getLastDeliveryDateofArticle(Long idArticle) {
        return supplyFeignClient.getLastDeliveryDateOfArticle(idArticle);
    }
}
