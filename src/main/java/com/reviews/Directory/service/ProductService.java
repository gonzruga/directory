package com.reviews.Directory.service;


import com.reviews.Directory.dto.BusinessDto;
import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final BusinessService businessService; //Added in order to connect product to a business.

    // CREATE - POST

    public Product saveProduct(ProductDto product, MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            product.setProductImageOne(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return repository.save(product.dtoToProduct());
    }

    public Product saveProduct1(ProductDto product) { return repository.save(product.dtoToProduct());}

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    // READ - GET

    // List 'GetProducts' Method

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
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
