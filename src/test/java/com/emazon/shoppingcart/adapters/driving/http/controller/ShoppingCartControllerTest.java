package com.emazon.shoppingcart.adapters.driving.http.controller;

import com.emazon.shoppingcart.adapters.driving.http.dto.request.ItemShoppingCartRequest;
import com.emazon.shoppingcart.adapters.driving.http.mapper.request.IShoppingCartRequestMapper;
import com.emazon.shoppingcart.configuration.jwt.JWTAuthFilter;
import com.emazon.shoppingcart.configuration.jwt.JwtService;
import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
