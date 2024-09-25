package com.emazon.shoppingcart.configuration;

import com.emazon.shoppingcart.adapters.driven.authentication.AuthenticationAdapter;
import com.emazon.shoppingcart.adapters.driven.feign.IStockFeignClient;
import com.emazon.shoppingcart.adapters.driven.feign.ISupplyFeignClient;
import com.emazon.shoppingcart.adapters.driven.feign.StockFeignAdapter;
import com.emazon.shoppingcart.adapters.driven.feign.SupplyFeignAdapter;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.adapter.ShoppingCartAdapter;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.mapper.IShoppingCartEntityMapper;
import com.emazon.shoppingcart.adapters.driven.jpa.mysql.repository.IShoppingCartRepository;
import com.emazon.shoppingcart.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
import com.emazon.shoppingcart.domain.api.usecase.ShoppingCartUseCaseImpl;
import com.emazon.shoppingcart.domain.spi.IAuthenticationPort;
import com.emazon.shoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.shoppingcart.domain.spi.IStockClient;
import com.emazon.shoppingcart.domain.spi.ISupplyClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IShoppingCartRepository shoppingCartRepository;
    private final IShoppingCartEntityMapper shoppingCartEntityMapper;
    private final IStockFeignClient stockFeignClient;
    private final ISupplyFeignClient supplyFeignClient;
    private final IArticleResponseMapper articleResponseMapper;

    @Bean
    public IAuthenticationPort authenticationPort() {
        return new AuthenticationAdapter();
    }

    @Bean
    public IStockClient stockClient() {
        return new StockFeignAdapter(stockFeignClient, articleResponseMapper);
    }

    @Bean
    public ISupplyClient supplyClient() {
        return new SupplyFeignAdapter(supplyFeignClient);
    }

    @Bean
    public IShoppingCartPersistencePort shoppingCartPersistencePort() {
        return new ShoppingCartAdapter(shoppingCartRepository, shoppingCartEntityMapper);
    }

    @Bean
    public IShoppingCartServicePort shoppingCartServicePort() {
        return new ShoppingCartUseCaseImpl(shoppingCartPersistencePort(), authenticationPort(), stockClient(), supplyClient());
    }
}
