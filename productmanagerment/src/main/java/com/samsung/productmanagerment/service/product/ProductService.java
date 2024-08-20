package com.samsung.bookmanagerment.service.product;

import com.samsung.bookmanagerment.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product book) throws Exception;
    List<Product> listProduct(String name,Long categoryId);
}
