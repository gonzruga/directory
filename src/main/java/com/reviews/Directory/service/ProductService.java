package com.reviews.Directory.service;


import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    // CREATE - POST

    public Product saveProduct(ProductDto product) {
        return repository.save(product.dtoToProduct());
    }

    // READ - GET

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // List 'GetProducts' Method
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return repository.findAll(keyword);
        }
        return repository.findAll();
    }


    // DELETE

    public String deleteProduct(long id) {
        repository.deleteById(id);
        return "Removed Product ID: " + id;
    }

    // UPDATE - PUT

    public Product updateProduct(ProductDto product){

        Product existingProduct = repository.findById(product.getId()).orElse(null);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setAlternativeNames(product.getAlternativeNames());
        existingProduct.setTags(product.getTag());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setDescription(product.getDescription());

        existingProduct.setUpdatedAt(new Date());

        return repository.save(existingProduct);
    }



}
