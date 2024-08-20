package com.samsung.bookmanagerment.controller;

import com.samsung.bookmanagerment.entity.request.OrderRequest;
import com.samsung.bookmanagerment.entity.response.BaseResponse;
import com.samsung.bookmanagerment.service.cart.CartService;
import com.samsung.bookmanagerment.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Transactional
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("v1/place-order")
    public ResponseEntity<?> getCarts(@RequestBody OrderRequest request) {
        try {

            return ResponseEntity.ok(orderService.placeOrder(request));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
}
