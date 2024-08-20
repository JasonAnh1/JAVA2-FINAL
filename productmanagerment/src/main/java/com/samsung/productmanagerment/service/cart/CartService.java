package com.samsung.bookmanagerment.service.cart;

import com.samsung.bookmanagerment.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartByUserLogin() throws Exception;
    List<Cart> addToCart( Long bookId, int quantity) throws Exception;
    List<Cart> deleteItemInCart(Long bookId) throws Exception;
}
