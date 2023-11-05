package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.utils.CdnUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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

//    private String logoUrl;   //storing. ???
    private MultipartFile logo;

    private Date createdAt = new Date();;
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

        Optional<String> optionalUrl = CdnUtils.uploadFile(logo);

        optionalUrl.ifPresent(business::setLogoUrl);

        return business;
    }
}

/*

{
    "id": "2",

    "businessName":"ABC Limited",
    "email":"office@gmail.com",
    "mobile":"0712345678",
    "physicalAddress":"12 Faru Road",
    "location":"Mwenge",
    "category":"Technology",
    "tinNumber":"123456",
    "link":"www.instagram.com/abcltd",

    "created_at":"2023-07-24 10:10:20",
    "updated_at":"2023-07-27 10:10:20"
}

*/

