package com.samsung.bookmanagerment.service.orders;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Cart;
import com.samsung.bookmanagerment.entity.Orders;
import com.samsung.bookmanagerment.entity.OrdersDetails;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.request.OrderRequest;
import com.samsung.bookmanagerment.repository.cart.CartRepository;
import com.samsung.bookmanagerment.repository.order.OrderDetailRepository;
import com.samsung.bookmanagerment.repository.order.OrderRepository;
import com.samsung.bookmanagerment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseService implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Orders placeOrder(OrderRequest request) throws Exception {
        User user = getUser();
        if (user == null || !user.getStatus()) {
            throw new Exception(Translator.toLocale("access_denied"));
        }
        List<OrdersDetails> ordersDetailsList = new ArrayList<>();
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
        if (cartList.size() < 1) {
            throw new Exception(Translator.toLocale("you dont have item in cart"));
        }
        Orders orders = new Orders();
        orders.setUserId(user.getId());
        orders.setTotalAmount(request.getTotalAmount());
        orders.setTotalQuantity(request.getTotalItem());
        orders.setStatus(true);
        Orders savedOrder = orderRepository.save(orders);
        for (Cart item : cartList) {
            OrdersDetails orderItem = new OrdersDetails();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setOrderId(savedOrder.getId());
            ordersDetailsList.add(orderItem);
        }
        orderDetailRepository.saveAll(ordersDetailsList);
        cartRepository.deleteAll(cartList);
        return savedOrder;
    }
}
