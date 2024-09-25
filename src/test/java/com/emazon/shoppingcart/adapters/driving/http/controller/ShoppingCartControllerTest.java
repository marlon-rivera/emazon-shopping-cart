package com.emazon.shoppingcart.adapters.driving.http.controller;

import com.emazon.shoppingcart.adapters.driving.http.dto.request.ItemShoppingCartRequest;
import com.emazon.shoppingcart.adapters.driving.http.mapper.request.IShoppingCartRequestMapper;
import com.emazon.shoppingcart.configuration.jwt.JWTAuthFilter;
import com.emazon.shoppingcart.configuration.jwt.JwtService;
import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
import com.emazon.shoppingcart.domain.model.ArticlesShoppingCart;
import com.emazon.shoppingcart.domain.model.ItemShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingCartController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ShoppingCartControllerTest {

    @MockBean
    private IShoppingCartServicePort shoppingCartServicePort;

    @MockBean
    private IShoppingCartRequestMapper shoppingCartRequestMapper;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

    @InjectMocks
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToShoppingCart_ShouldReturnOk_WhenRequestIsValid() throws Exception {
        ItemShoppingCartRequest request = new ItemShoppingCartRequest(1L, BigInteger.TEN);
        ItemShoppingCart itemShoppingCart = new ItemShoppingCart(1L, 1L, BigInteger.TEN);

        when(shoppingCartRequestMapper.toItemShoppingCart(request)).thenReturn(itemShoppingCart);

        mockMvc.perform(post("/shopping-cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void removeFromShoppingCart_ShouldReturnOk_WhenItemIsRemoved() throws Exception {
        Long idArticle = 1L;
        doNothing().when(shoppingCartServicePort).removeItemShoppingCart(idArticle);

        mockMvc.perform(delete("/shopping-cart/{idArticle}", idArticle)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingCartServicePort).removeItemShoppingCart(idArticle);
    }

    @Test
     void testGetAllArticlesOfShoppingCart() throws Exception {

        int page = 0;
        int size = 10;
        String order = "ASC";
        List<Long> idsCategories = List.of(1L, 2L);
        List<Long> idsBrands = List.of(1L, 2L);

        ArticlesShoppingCart articlesShoppingCart = new ArticlesShoppingCart(null, null);
        when(shoppingCartServicePort.getArticlesOfShoppingCart(page, size, order, idsCategories, idsBrands)).thenReturn(articlesShoppingCart);

        mockMvc.perform(get("/shopping-cart/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("order", order)
                        .param("idsCategories", "1,2")
                        .param("idsBrands", "1,2"))
                .andExpect(status().isOk());

        verify(shoppingCartServicePort, times(1)).getArticlesOfShoppingCart(page, size, order, idsCategories, idsBrands);
    }
}
