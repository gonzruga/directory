package com.reviews.Directory.controller;

import com.reviews.Directory.dto.BusinessDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.service.BusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Slf4j
@Controller
//@RestController // For returning data, not template
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService service;
    private final Environment env; //SpringFrameworkCore.env

// FORMS & PAGES

    // Form to Create Business
    @GetMapping("/businessForm")
    public String businessForm(Model model) {
        // Create BUSINESS object.
        BusinessDto business = new BusinessDto();

        // Add BUSINESS object as a model attribute.
        model.addAttribute("business", business);  // Name & value of attribute.
        return "business-create-form";
    }

    // Form to display details of Business created.
    @PostMapping("/businessSubmit")
    public String businessSubmit(@ModelAttribute BusinessDto business, Model model, @RequestParam("imageFile") MultipartFile file) {
        model.addAttribute("business", business);  // Name & value of attribute.
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if(fileName.contains(".."))
//        {
//            System.out.println("not a a valid file");
//        }
//        try {
//            business.setLogo(Base64.getEncoder().encodeToString(file.getBytes()));
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }

        service.saveBusinessAndLogo(business, file);
        return "business-create-submit";
    }

    //Directory | Homepage
    @GetMapping("/businessList")
    public String findAllBusinesses(Model model){
        model.addAttribute("business",service.getBusinesses());
        return "business-list";
    }

    @GetMapping("/businessPage/{id}")
    public String businessPage(@PathVariable long id, Model model) {
        model.addAttribute("business", service.getBusinessById(id));  // Name & value of attribute.
        return "business-page";
    }

    @GetMapping("/businessEdit/{id}")
    public String editBusiness(@PathVariable long id, Model model){
        Business business = new Business();
        business = service.getBusinessById(id);
        model.addAttribute("business", business);
        return "business-edit";  //Directed to 'update/{id}'
    }

// UPDATE - PUT

    @PostMapping("/businessUpdate/{id}")
    public String updateBusiness(@ModelAttribute BusinessDto business, @PathVariable long id) {
        service.updateBusiness(business);
        return "redirect:/businessPage/{id}";
    }

// END OF FORMS AND PAGES
//-------------------------------------------------------------------------------------------------


//START OF STANDARD CRUD

// CREATE - POST
    
    @PostMapping("/addBusiness")
    public Business addBusiness(@RequestBody BusinessDto business) {
        return service.saveBusiness(business);
    }

    @PostMapping("/addBusinesses")
    public List<Business> addBusinesses(@RequestBody List<Business> businesses) {
        return service.saveBusinesses(businesses);
    }

// READ - GET

    //With Response Entity
    @GetMapping("/businesses1")
    public ResponseEntity<List<Business>> findAllBusinesses1(){
        return  ResponseEntity.ok(service.getBusinesses());
    }

    @GetMapping("/businesses2")
    public List<Business> findAllBusinesses2(){
       return  service.getBusinesses();
    }
    
    @GetMapping("/business/{id}")
    public Business findBusinessById(@PathVariable long id){
        return service.getBusinessById(id);
    }

    @GetMapping("/name/{businessName}")  // Works only for unique names
    public Business findBusinessByBusinessName(@PathVariable String businessName){
        return service.getBusinessByBusinessName(businessName);  }


    
// DELETE

    @GetMapping("/businessDelete/{id}")
    public String deleteBusiness(@PathVariable long id){
        service.deleteBusiness(id);
        return "redirect:/businessList";
    }



//-------------------------------------------------------------------------------------------------

    // STORING IMAGES LOCALLY

    @GetMapping(value = "/businessFormLocal")
    public String businessFormLocalStore(BusinessDto _business, Model model, HttpServletRequest request) {
        try {
            Business business = service.saveBusiness(_business);
            if (business == null) {
                model.addAttribute("message", "Error registering car");
                return "business-create-form";
            }
            request.getParts().forEach((part -> {
                log.info(part.getSubmittedFileName());
            }));
//            request.getSession().setAttribute("id", business.getId());
            return "redirect:/businessList";
        } catch (Exception e) {
            model.addAttribute("message", "Error creating business");
            return "business-create-form";
        }
    }

    @RequestMapping(value = "/businessSubmitLocal",method=RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String businessSubmitLocalStore(@ModelAttribute BusinessDto business, @RequestPart List<MultipartFile> businessLogo) throws Exception {
        businessLogo.forEach((file)->{
            try {
                File _file = new File(env.getProperty("files_location") + file.getOriginalFilename());
                log.info(file.getOriginalFilename());
                Files.copy(file.getInputStream(), _file.toPath());
            }catch(IOException e){
                log.error(e.getMessage(),e.getClass().getSimpleName(),e);
            }
        });
        service.saveBusiness(business);
        return "redirect:/businessList";
    }
// END OF STORING IMAGES LOCALLY



}
