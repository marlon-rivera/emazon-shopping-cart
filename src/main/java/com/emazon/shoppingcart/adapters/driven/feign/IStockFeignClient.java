package com.emazon.shoppingcart.adapters.driven.feign;

import com.emazon.shoppingcart.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.shoppingcart.adapters.driving.http.dto.response.PaginationInfoResponse;
import com.emazon.shoppingcart.domain.model.PurchaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "emazon-stock", url = "http://localhost:8082/article")
public interface IStockFeignClient {

    @GetMapping("/quantity/{id}")
    BigInteger getQuantityOfArticle(@PathVariable("id") Long id);

    @GetMapping("/categories/{id}")
    List<Long> getCategoriesOfArticle(@PathVariable("id") Long id);

    @GetMapping("/shopping-cart")
    PaginationInfoResponse<ArticleResponse> getArticlesOfShoppingCart(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("idsArticles") List<Long> idsArticles,
            @RequestParam("order") String order,
            @RequestParam("idsCategories") List<Long> idsCategories,
            @RequestParam("idsBrands") List<Long> idsBrands
    );

    @PostMapping("/purchase")
    void purchase(@RequestBody PurchaseRequest purchaseRequest);

}
