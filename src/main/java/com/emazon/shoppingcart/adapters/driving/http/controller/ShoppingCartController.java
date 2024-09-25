package com.emazon.shoppingcart.adapters.driving.http.controller;

import com.emazon.shoppingcart.adapters.driving.http.dto.request.ItemShoppingCartRequest;
import com.emazon.shoppingcart.adapters.driving.http.mapper.request.IShoppingCartRequestMapper;
import com.emazon.shoppingcart.configuration.exceptionhandler.ExceptionResponse;
import com.emazon.shoppingcart.configuration.exceptionhandler.ValidationExceptionResponse;
import com.emazon.shoppingcart.domain.api.IShoppingCartServicePort;
import com.emazon.shoppingcart.domain.model.ArticlesShoppingCart;
import com.emazon.shoppingcart.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> removeFromShoppingCart(@PathVariable("idArticle") Long id) {
        shoppingCartServicePort.removeItemShoppingCart(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get all articles in the shopping cart",
            description = "This endpoint retrieves all articles in the shopping cart with pagination, sorting, and filtering by category and brand IDs."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the articles in the shopping cart.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArticlesShoppingCart.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request due to incorrect parameters.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationExceptionResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error while processing the request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    )
    @GetMapping("/")
    public ResponseEntity<ArticlesShoppingCart> getAllArticlesOfShoppingCart(
            @Parameter(description = "Page number to list", example = "0")
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_VALUE_NUMBER_PAGE) @Min(value = Constants.MIN_VALUE_PAGE, message = Constants.EXCEPTION_MIN_VALUE_PAGE) int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_VALUE_SIZE_PAGE) @Min(value = Constants.MIN_VALUES_PER_PAGE, message = Constants.EXCEPTION_MIN_VALUES_PER_PAGE) int size,
            @Parameter(description = "Sort order (e.g., 'ASC' for ascending or 'DESC' for descending)", example = "ASC")
            @RequestParam(value = "order", defaultValue = Constants.ORDER_ASC) @Pattern(regexp = Constants.REGEX_ORDER, message = Constants.EXCEPTION_REGEX_ORDER) String order,
            @Parameter(description = "List of category IDs to filter by", example = "[1, 2, 3]")
            @RequestParam(value = "idsCategories", defaultValue = "") List<Long> idsCategories,
            @Parameter(description = "List of brand IDs to filter by", example = "[1, 2, 3]")
            @RequestParam(value = "idsBrands", defaultValue = "") List<Long> idsBrands
    ) {
        return ResponseEntity.ok(
                shoppingCartServicePort.getArticlesOfShoppingCart(page, size, order, idsCategories, idsBrands)
        );
    }

}
