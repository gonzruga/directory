package com.reviews.Directory.controller;

import com.reviews.Directory.dto.BusinessDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.utils.CdnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Controller
//@RestController // For returning data, not template
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService service;

    @GetMapping("/businessForm")
    public String businessForm(Model model) {
        // Create BUSINESS object.
        BusinessDto business = new BusinessDto();

        // Add BUSINESS object as a model attribute.
        model.addAttribute("business", business);  // Name & value of attribute.
        return "business-create-form";
    }

    @PostMapping("/businessSubmit")
    public String saveBusiness(@ModelAttribute BusinessDto business, Model model, @RequestParam("imageFile") MultipartFile multipartFile) {
        log.info(multipartFile.getName());
        log.info(multipartFile.getOriginalFilename());

        model.addAttribute("business", business);
        if (multipartFile != null) {
            if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                Optional<String> optionalUrl = CdnUtils.uploadFile(multipartFile);
                optionalUrl.ifPresent(business::setLogoUrl);
            }
        }
        service.saveBusiness(business);
        return "business-create-submit";
    }

    //Directory | Homepage
    @GetMapping("/businessList")
    public String findAllBusinesses(Model model){
        model.addAttribute("business", service.getBusinesses());
        return "business-list";
    }

    @GetMapping("/businessPage/{id}")
    public String businessPage(@PathVariable long id, Model model) {
        model.addAttribute("business", service.getBusinessById(id));
        return "business-page";
    }

    @GetMapping("/business/{id}")
    public Business findBusinessById(@PathVariable long id){
        return service.getBusinessById(id);
    }

    // UPDATE - PUT
    @GetMapping("/businessEdit/{id}")
    public String editBusiness(@PathVariable long id, Model model){
        Business business = new Business();
        business = service.getBusinessById(id);
        model.addAttribute("business", business);
        return "business-edit";  //Directed to 'update/{id}'
    }

    @PostMapping("/businessUpdate/{id}")
    public String updateBusiness(@ModelAttribute BusinessDto business, @PathVariable long id) {
        service.updateBusiness(business);
        return "redirect:/businessPage/{id}";
    }

// DELETE

    @GetMapping("/businessDelete/{id}")
    public String deleteBusiness(@PathVariable long id){
        service.deleteBusiness(id);
        return "redirect:/businessList";
    }


}
