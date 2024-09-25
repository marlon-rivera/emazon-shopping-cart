package com.emazon.shoppingcart.adapters.driving.http.mapper.response;

import com.emazon.shoppingcart.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.shoppingcart.adapters.driving.http.dto.response.PaginationInfoResponse;
import com.emazon.shoppingcart.domain.model.Article;
import com.emazon.shoppingcart.domain.model.PaginationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleResponseMapper {

    @Mapping(target = "id", source = "article.id")
    @Mapping(target = "name", source = "article.name")
    @Mapping(target = "description", source = "article.description")
    @Mapping(target = "quantity", source = "article.quantity")
    @Mapping(target = "price", source = "article.price")
    @Mapping(target = "brand", source = "article.brand")
    @Mapping(target = "categories", source = "article.categories")
    ArticleResponse toArticleResponse(Article article);

    Article toArticle(ArticleResponse articleResponse);

    List<ArticleResponse> toListArticleResponse(List<Article> articles);

    PaginationInfoResponse<ArticleResponse> toPaginationInfoResponse(PaginationInfo<Article> paginationInfo);

    @Mapping(target = "list", source = "paginationInfoResponse.paginationInfo.list")
    @Mapping(target = "currentPage", source = "paginationInfoResponse.paginationInfo.currentPage")
    @Mapping(target = "pageSize", source = "paginationInfoResponse.paginationInfo.pageSize")
    @Mapping(target = "totalElements", source = "paginationInfoResponse.paginationInfo.totalElements")
    @Mapping(target = "totalPages", source = "paginationInfoResponse.paginationInfo.totalPages")
    @Mapping(target = "hasNextPage", source = "paginationInfoResponse.paginationInfo.hasNextPage")
    @Mapping(target = "hasPreviousPage", source = "paginationInfoResponse.paginationInfo.hasPreviousPage")
    PaginationInfo<Article> toPaginationInfo(PaginationInfoResponse<ArticleResponse> paginationInfoResponse);
}
