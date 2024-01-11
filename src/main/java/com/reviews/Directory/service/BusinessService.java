package com.reviews.Directory.service;


import com.reviews.Directory.dto.BusinessDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.repository.BusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class BusinessService {

    private BusinessRepository repository;  // The service communicates with Repo. so Repo needs to be initialized.

// CREATE - POST
    public Business saveBusiness(BusinessDto business) {return repository.save(business.dtoToBusiness());}

// READ - GET
    public List<Business> getBusinesses() {
        return repository.findAll();
    }

    public Business getBusinessById(Long id) {
        return repository.findById(id).orElse(null);
    }


// DELETE
    public String deleteBusiness(long id){
        repository.deleteById(id);
        return "Business removed with ID number: " + id;
    }

// UPDATE - PUT
    public Business updateBusiness(BusinessDto business){
        Business existingBusiness = repository.findById(business.getId()).orElse(null);
        // There is no UPDATE method in Spring Data JPA
        existingBusiness.setBusinessName(business.getBusinessName());
        existingBusiness.setEmail(business.getEmail());
        existingBusiness.setMobile(business.getMobile());
        existingBusiness.setPhysicalAddress(business.getPhysicalAddress());
        existingBusiness.setLocation(business.getLocation());
        existingBusiness.setCategory(business.getCategory());
        existingBusiness.setTinNumber(business.getTinNumber());

        existingBusiness.setLink(business.getLink());

        existingBusiness.setLogoUrl(business.getLogoUrl());

        existingBusiness.setUpdatedAt( new Date() );

        return repository.save(existingBusiness);

}


}
