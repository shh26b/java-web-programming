package com.shihabmahamud.eshoppers.service;

import com.shihabmahamud.eshoppers.domain.Product;
import com.shihabmahamud.eshoppers.dto.ProductDTO;
import com.shihabmahamud.eshoppers.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
       this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAllProductSortedByName() {
        var products = productRepository.findAllProduct();
        if (products == null) {
            throw new RuntimeException("There is no product in db");
        }
        var productDTOS = convertToProductDTO(products);
        return sortProductsByName(productDTOS);
    }

    private List<ProductDTO> convertToProductDTO(List<Product> products) {
        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            productsDTO.add(new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getRating()
                )
            );
        }
        return productsDTO;
    }

    private List<ProductDTO> sortProductsByName(List<ProductDTO> products) {
        var sortedProducts = products
                .stream()
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());
        var isAsc = true;
        if (!isAsc) Collections.reverse(sortedProducts);
        return sortedProducts;
    }
}
