package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.service.ProductService;
import com.reviews.Directory.utils.CdnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
        return "product-form";
    }

    @PostMapping("/productSubmit")
    public String productSubmit(@ModelAttribute ProductDto product, Model model, long id,
                                @RequestParam("imageFileOne") MultipartFile multipartFile) {
        model.addAttribute("product", product);
        Optional<String> optionalUrl = CdnUtils.uploadFile(multipartFile);
        optionalUrl.ifPresent(product::setImageOneUrl);

        product.setProductSubject(businessService.getBusinessById(id));
        log.info("{}",id);
        product.setProductSubjectId(id);

        service.saveProduct(product);
        return "redirect:/businessList";
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
        return "product-page";
    }

    @RequestMapping("/productList1")
    public String productListPage(Model model) {
        String keyword = "";

        List<Product> listProducts = service.listAll(keyword);
        model.addAttribute("listProducts", listProducts);
        return "product-list";
    }

    @GetMapping("/productList")
    public String findAllProducts(Model model, @Param("keyword") String keyword){
        model.addAttribute("product",service.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "product-list";
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
