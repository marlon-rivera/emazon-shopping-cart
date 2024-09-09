package com.emazon.shoppingcart.adapters.driving.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    @PostMapping("/")
    public ResponseEntity<String> addToShoppingCart(){
        return ResponseEntity.ok("Soy usuario añadir");
    }

    @DeleteMapping("/")
    public ResponseEntity<String> removeFromShoppingCart(){
        return ResponseEntity.ok("Soy usuario eliminar");
    }



}
