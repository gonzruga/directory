package com.reviews.Directory.dto;


import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Tag;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    private String tags;  //Not to be comma separated
    private List<Tag> tagList;

    private String brand;
    private Double price;

    private String description;

    private String imageOneUrl;
    private String imageTwoUrl;


    private Business productSubject;
    private Long productSubjectId;

    private Date createdAt  = new Date();
    private Date updatedAt;

    public Product dtoToProduct() {
        final Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setAlternativeNames(alternativeNames);
//        product.setTags(tags);
        product.setTagList(tagList);

        product.setBrand(brand);
        product.setPrice(price);
        product.setDescription(description);

        product.setImageOneUrl(imageOneUrl);
        product.setImageTwoUrl(imageTwoUrl);

        product.setProductSubject(productSubject);

        return product;
    }

}
