package com.samsung.bookmanagerment.service.cart;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Cart;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import com.samsung.bookmanagerment.repository.cart.CartRepository;
import com.samsung.bookmanagerment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends BaseService implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getCartByUserLogin() throws Exception {
        User user = getUser();
        if (user == null) {
            throw new Exception(Translator.toLocale("access_denied"));
        }
        return cartRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Cart> addToCart(Long productId, int quantityIn) throws Exception {
        User user = getUser();
        if (user == null) {
            throw new Exception(Translator.toLocale("access_denied"));
        }
        Cart cart;
        if (cartRepository.existsByProductIdAndUserId(productId, user.getId())) {
            cart = cartRepository.findFirstByProductIdAndUserId(productId, user.getId());
            cart.setQuantity(cart.getQuantity() + quantityIn);
        }
        else
        {
            cart = new Cart();
            cart.setUserId(user.getId());
            cart.setProductId(productId);
            cart.setQuantity(quantityIn);
        }
        cartRepository.save(cart);
        return cartRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Cart> deleteItemInCart(Long productId) throws Exception {
        User user = getUser();
        if (user == null) {
            throw new Exception(Translator.toLocale("access_denied"));
        }
        if (cartRepository.existsByProductIdAndUserId(productId, user.getId())) {
            Cart cart = cartRepository.findFirstByProductIdAndUserId(productId, user.getId());
            if (cart.getQuantity() > 1) {
                cart.setQuantity(cart.getQuantity() - 1);
                cartRepository.save(cart);
            } else {
                cartRepository.delete(cart);
            }
        }
        return cartRepository.findAllByUserId(user.getId());
    }
}
