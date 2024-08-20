package com.samsung.bookmanagerment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long userId;
    public int totalQuantity;
    public Long totalAmount;
    public Boolean  status;

}
