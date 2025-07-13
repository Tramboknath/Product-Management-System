package com.example.service;

import com.example.dto.ProductDto;
import com.example.model.Product;

import java.util.List;

public interface ProductService {

    public Boolean saveProduct(ProductDto productDto);
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(Integer id);
    public Boolean deleteProduct(Integer id);
}
