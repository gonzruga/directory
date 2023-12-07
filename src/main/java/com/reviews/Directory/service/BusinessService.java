package com.reviews.Directory.service;


import com.reviews.Directory.dto.BusinessDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;  // The service communicates with Repo. so Repo needs to be initialized.

// CREATE - POST
    public Business saveBusinessAndLogo(BusinessDto business, MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            business.setLogo(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return repository.save(business.dtoToBusiness());
    }

    public Business saveBusiness(BusinessDto business) { return repository.save(business.dtoToBusiness()); }
    public List<Business> saveBusinesses(List<Business> businesses) {
        return repository.saveAll(businesses);
    }

// READ - GET
    public List<Business> getBusinesses() {
        return repository.findAll();
    }

    public Business getBusinessById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Business getBusinessByBusinessName(String businessName) {
        return repository.findByBusinessName(businessName);
        // This is a custom method. It has to be made in repository.
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

//        existingBusiness.setBusinessLogo(business.getBusinessLogo());

        existingBusiness.setLink(business.getLink());

//        existingPerson.setCreatedAt(person.getCreatedAt());

        existingBusiness.setUpdatedAt( new Date() );

        return repository.save(existingBusiness);

}


}
