package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    private final BusinessService businessService;

    // CREATE - POST

    @GetMapping("/productForm/{businessId}")
    public String productCreate(
            @PathVariable(name="businessId") Long businessId,
            Model model
    ) {
        Product product = new Product();
        model.addAttribute("businessId", businessId);
        model.addAttribute("product", product);
        return "product-form-DB";
    }

    @PostMapping("/productSubmit")
    public String productSubmit(@ModelAttribute ProductDto product, Model model, @RequestParam("imageFileOne") MultipartFile file) {
        model.addAttribute("product", product);
        service.saveProduct(product, file);
        return "product-submit-DB";
    }

    // READ - GET

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable long id){
        return service.getProductById(id);
    }

    @GetMapping("/productPage/{id}")
    public String productPage(@PathVariable long id, Model model){
        Product productById = service.getProductById(id);
        model.addAttribute("product", productById);
        return "product-page-DB";
    }

    // UPDATE - PUT
//    @PostMapping("/productUpdate/{id}")
//    public String productEdit(@PathVariable long id,){
//
//        return null;
//    }


    // DELETE
    @GetMapping("/productDelete/{id}")
    public String deleteProduct(@PathVariable long id) {
        service.deleteProduct(id);
        return "redirect:/businessList";
    }


}
