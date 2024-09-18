package com.emazon.shoppingcart.adapters.driving.http.dto.request;

import com.emazon.shoppingcart.utils.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemShoppingCartRequest {

    @NotNull(message = Constants.EXCEPTION_ARTICLE_NOT_EMPTY)
    private Long idArticle;
    @NotNull(message = Constants.EXCEPTION_QUANTITY_NOT_EMPTY)
    private BigInteger quantity;

}
