package com.emazon.shoppingcart.adapters.driven.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(name = "emazon-supply", url = "http://localhost:8083/supply")
public interface ISupplyFeignClient {

    @GetMapping("/last-delivery-date/{idArticle}")
    LocalDate getLastDeliveryDateOfArticle(@PathVariable("idArticle") Long idArticle);

}
