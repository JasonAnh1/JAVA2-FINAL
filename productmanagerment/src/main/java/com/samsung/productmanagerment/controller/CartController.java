package com.samsung.bookmanagerment.controller;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Cart;
import com.samsung.bookmanagerment.entity.response.BaseResponse;
import com.samsung.bookmanagerment.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Transactional
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    @GetMapping("v1/get-carts")
    public ResponseEntity<?> getCarts() {
        try {

            return ResponseEntity.ok(cartService.getCartByUserLogin());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @GetMapping("v1/delete-cart-item")
    public ResponseEntity<?> getCarts(@RequestParam Long productId) {
        try {

            return ResponseEntity.ok(cartService.deleteItemInCart(productId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @PostMapping("v1/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Cart request) {
        try {
            if(request == null
                    || request.getProductId() == null
                    || request.getQuantity() == 0) {
                throw new Exception(Translator.toLocale("required_fields"));
            }
            return ResponseEntity.ok(cartService.addToCart(request.getProductId(),request.quantity));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }

}
