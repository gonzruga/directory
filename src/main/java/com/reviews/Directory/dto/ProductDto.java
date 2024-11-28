package com.reviews.Directory.dto;


import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Tag;
import com.reviews.Directory.dto.TagDto;

import lombok.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
//    private List<TagDto>   tagList;  // Nested Tag DTOs if needed

    private String brand;
    private Double price;

    private String description;
    private String paymentLink;

    private String imageOneUrl;
    private String imageTwoUrl;

//    private boolean sponsored;
    private int sponsorLevel;
    private Date paymentDate;

    private Long productSubjectId;  //Only here, used in productSubmit
    private Business productSubject;

    private Date createdAt  = new Date();
    private Date updatedAt;

    public Product dtoToProduct() {
        final Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setAlternativeNames(alternativeNames);
        product.setTagList(tagList);

//        // Convert List<TagDto> to List<Tag> if tagList is populated
//        if (tagList != null) {
//            List<Tag> tags = tagList.stream()
//                    .map(tagDto -> new Tag(tagDto.getId(), tagDto.getTagTitle(), tagDto.getTagDescription()))
//                    .collect(Collectors.toList());
//            product.setTagList(tags);
//        }

        product.setBrand(brand);
        product.setPrice(price);
        product.setDescription(description);
        product.setPaymentLink(paymentLink);
        product.setImageOneUrl(imageOneUrl);
        product.setImageTwoUrl(imageTwoUrl);

//        product.setSponsored(sponsored);
        product.setSponsorLevel(sponsorLevel);

        product.setProductSubject(productSubject);

        return product;
    }

}
