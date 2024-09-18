package com.emazon.shoppingcart.utils;

public class Constants {

    private Constants() {}

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";
    public static final String ROLE_ = "ROLE_";
    public static final String ROLE = "ROLE";
    public static final int MAXIMUM_ARTICLES_BY_CATEGORY = 3;
    public static final String EXCEPTION_ARTICLE_NOT_EMPTY = "El id del articulo no puede estar vacio.";
    public static final String EXCEPTION_CATEGORY_NOT_EMPTY = "El id de la categoria no puede estar vacio.";
    public static final String EXCEPTION_QUANTITY_NOT_EMPTY = "La cantidad a agregar no puede ser 0";
    public static final String EXCEPTION_SHOPPING_CART_SERVER_ERROR = "En estos momentos no se puede añadir articulos al carrito.";
    public static final String EXCEPTION_SHOPPING_CART_ARTICLE_NOT_FOUND = "El articulo que quiere agregar al carrito de compras no existe.";
    public static final String EXCEPTION_SHOPPING_CART_MAXIMUM_ARTICLES_BY_CATEGORY = "No puede exceder el maximo de articulos por categoria.";
    public static final String EXCEPTION_SHOPPING_CART_QUANTITY_ZERO = "La cantidad a agregar del articulo no puede ser 0.";
    public static final String EXCEPTION_SHOPPING_CART_UNITS_NOT_AVALAIBLE = "La cantidad solicitada del articulo no se encuentra en stock.";
    public static final String EXCEPTION_SHOPPING_CART_UNITS_ZERO = "En estos momentos el articulo se encuentra sin stock, la proxima fecha de abastecimiento es: ";
    public static final int DAYS_OF_NEXT_SUPPLY = 15;
}
