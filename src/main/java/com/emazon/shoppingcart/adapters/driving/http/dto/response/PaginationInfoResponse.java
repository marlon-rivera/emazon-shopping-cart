package com.emazon.shoppingcart.adapters.driving.http.dto.response;

import com.emazon.shoppingcart.domain.model.PaginationInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfoResponse<T> {

    private PaginationInfo<T> paginationInfo;

}
