package com.samsung.bookmanagerment.service.orders;

import com.samsung.bookmanagerment.entity.Orders;
import com.samsung.bookmanagerment.entity.request.OrderRequest;

public interface OrderService {
    Orders placeOrder(OrderRequest request) throws Exception;
}
