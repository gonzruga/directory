package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.Business;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BusinessDto {
    private Long id;

    private String businessName;
    private String email;
    private String mobile;

    private String physicalAddress;
    private String location;

    private String category;
    private String tinNumber;
    private String link;

    private String logo;

    //    private String logoUrl;

    private Date createdAt = new Date();
    private Date updatedAt = null;

    public Business dtoToBusiness() {

        final Business business = new Business();

        business.setId(id);

        business.setBusinessName(businessName);
        business.setEmail(email);
        business.setMobile(mobile);

        business.setPhysicalAddress(physicalAddress);
        business.setLocation(location);

        business.setCategory(category);
        business.setTinNumber(tinNumber);
        business.setLink(link);

        // When storing images to MySql
        business.setLogo(logo);

        // When storing images to Fasthub CDN.Utils & Amazon AWS
//        business.setLogoUrl(logUrl);


        return business;
    }
}