package com.samsung.bookmanagerment.repository.order;

import com.samsung.bookmanagerment.entity.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrdersDetails,Long> {
}
