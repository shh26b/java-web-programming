package com.shihabmahamud.eshoppers.repository;

import com.shihabmahamud.eshoppers.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAllProduct();
    Product findById(Long productId);
}