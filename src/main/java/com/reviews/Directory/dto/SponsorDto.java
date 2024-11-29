package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Sponsor;
import lombok.Data;

import java.util.Date;
@Data
public class SponsorDto {

    private Long id;
    private int sponsorLevel;
    private double fee;

    private String reference;

//    private boolean paymentStatus;

    private Long productId;
    private Product product;

    private Date createdAt  = new Date();
    private Date updatedAt;

    public Sponsor dtoToSponsor(){
        final Sponsor sponsor = new Sponsor();

        sponsor.setId(id);
        sponsor.setSponsorLevel(sponsorLevel);
        sponsor.setFee(fee);
        sponsor.setReference(reference);
//        sponsor.setPaymentStatus(paymentStatus);

        sponsor.setProduct(product);

        return sponsor;
    }
}
