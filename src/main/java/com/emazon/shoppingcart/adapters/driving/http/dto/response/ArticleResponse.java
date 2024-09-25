package com.emazon.shoppingcart.adapters.driving.http.dto.response;

import com.emazon.shoppingcart.domain.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Brand brand;
    private Set<CategoryArticleResponse> categories;

}
