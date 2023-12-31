package com.reviews.Directory.dto;


import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Product;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String productName;
    private String alternativeNames;
//    private String alternativeName2;
    private String tag;  //Not to be comma separated
    private String brand;
    private Double price;

    private String description;

    // This variable is used when storing images to MySql database
    private String productImageOne;
    private String productImageTwo;


    private Business productSubject;
    private Long productSubjectId;

    private Date createdAt  = new Date();
    private Date updatedAt;

    public Product dtoToProduct() {
        final Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setAlternativeNames(alternativeNames);
        product.setTags(tag);
        product.setBrand(brand);
        product.setPrice(price);
        product.setDescription(description);

        // When storing images to MySql
        product.setProductImageOne(productImageOne);
        product.setProductImageTwo(productImageTwo);


        return product;
    }

}
