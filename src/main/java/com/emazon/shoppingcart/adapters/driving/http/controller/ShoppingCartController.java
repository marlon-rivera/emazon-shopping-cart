package com.emazon.shoppingcart.adapters.driving.http.controller;

import com.emazon.shoppingcart.adapters.driving.http.dto.request.ItemShoppingCartRequest;
import com.emazon.shoppingcart.adapters.driving.http.mapper.request.IShoppingCartRequestMapper;
import com.emazon.shoppingcart.configuration.exceptionhandler.ExceptionResponse;
import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final IShoppingCartServicePort shoppingCartServicePort;
    private final IShoppingCartRequestMapper shoppingCartRequestMapper;

    @Operation(
            summary = "Add item to shopping cart",
            description = "This endpoint allows you to add an item to the shopping cart. The item details are provided in the request body."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item added to the shopping cart successfully.",
            content = @Content
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request body. Ensure that all required fields are correctly provided and validated.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error while processing the request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    )
    @PostMapping("/")
    public ResponseEntity<Void> addToShoppingCart(@Valid @RequestBody ItemShoppingCartRequest request) {
        shoppingCartServicePort.addItemShoppingCart(shoppingCartRequestMapper.toItemShoppingCart(request));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Remove item from shopping cart",
            description = "This endpoint allows you to remove an item from the shopping cart by specifying the item's ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item removed from the shopping cart successfully.",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Item not found in the shopping cart.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error while processing the request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    )
    @DeleteMapping("/{idArticle}")
    public ResponseEntity<String> removeFromShoppingCart(@PathVariable("idArticle") Long id) {
        shoppingCartServicePort.removeItemShoppingCart(id);
        return ResponseEntity.ok().build();
    }
}
