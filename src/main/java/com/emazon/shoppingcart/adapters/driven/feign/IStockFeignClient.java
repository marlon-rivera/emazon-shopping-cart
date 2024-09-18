package com.emazon.shoppingcart.adapters.driven.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "emazon-stock", url = "http://localhost:8082/article")
public interface IStockFeignClient {

    @GetMapping("/quantity/{id}")
    BigInteger getQuantityOfArticle(@PathVariable("id") Long id);

    @GetMapping("/categories/{id}")
    List<Long> getCategoriesOfArticle(@PathVariable("id") Long id);
}
