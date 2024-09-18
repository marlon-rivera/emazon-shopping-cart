package com.emazon.shoppingcart.domain.spi;

import java.time.LocalDate;

public interface ISupplyClient {

    LocalDate getLastDeliveryDateofArticle(Long idArticle);

}
