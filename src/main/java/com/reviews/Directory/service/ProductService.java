package com.reviews.Directory.service;


import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.repository.ProductRepository;
import com.reviews.Directory.utils.ProductUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    // CREATE - POST

    public Product saveProduct(ProductDto product) {
        return repository.save(product.dtoToProduct());
    }

    public Product acceptProduct(Product product) {
        return repository.save(product);
    }


    // READ - GET

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ProductList -> findAllProducts
    public List<Product> listAll(String keyword) {
//        if (keyword != null) {
//            return repository.findAllRandomOrder(keyword);
////            return repository.findAll(keyword);
//        }
//        return repository.findAllRandomOrder();
        List<Product> products;

        if (keyword != null && !keyword.isEmpty()) {
            products = repository.findAllRandomOrder(keyword);
        } else {
            products = repository.findAllRandomOrder();
        }

        // Update sponsor level for each product if payment date is expired
        List<Product> productList = products.stream().map(
                product -> {
                    Product product1 = ProductUtils.updateSponsorLevelIfExpired(product);
                    return repository.save(product1);
                }
        ).collect(Collectors.toList());
        return productList;
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

        existingProduct.setTagList(product.getTagList());

        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());

        existingProduct.setDescription(product.getDescription());
        existingProduct.setDescription(product.getPaymentLink());

//        existingProduct.setImageOneUrl(product.getImageOneUrl());
//        existingProduct.setImageTwoUrl(product.getImageTwoUrl());

//        existingProduct.setSponsored(product.isSponsored());
        existingProduct.setSponsorLevel(product.getSponsorLevel());
        existingProduct.setPaymentDate(product.getPaymentDate());

        existingProduct.setUpdatedAt(new Date());

        return repository.save(existingProduct);
    }

}
