package com.samsung.bookmanagerment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders_details")
@Getter
@Setter
public class OrdersDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long orderId;
    private Long productId;
    public Long unitPrice;
    public int quantity;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable = false, updatable = false)
    private Orders orders;
}
