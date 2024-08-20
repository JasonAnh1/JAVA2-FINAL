package com.samsung.bookmanagerment.repository.cart;

import com.samsung.bookmanagerment.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long userId);

    //    @Procedure(name = "add_to_cart")
//    void addToCart(@Param("user_id") Long userId,@Param("book_id") Long bookId,@Param("quantityIn") int quantityIn);
//
//    @Procedure(name = "remove_from_cart")
//    void removeFromCart(Long userId, Long bookId);
    Boolean existsByProductIdAndUserId(Long bookId, Long userId);
    Cart findFirstByProductIdAndUserId(Long bookId, Long userId);
}
