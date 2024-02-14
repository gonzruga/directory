package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Tag;
import com.reviews.Directory.repository.TagRepository;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.service.ProductService;
import com.reviews.Directory.service.TagService;
import com.reviews.Directory.utils.CdnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private final BusinessService businessService;

    @Autowired
    private final TagService tagService;

    @Autowired
    private final TagRepository tagRepository;

    // CREATE - POST

    @GetMapping("/productForm/{businessId}")
    public String productCreate(
            @PathVariable(name="businessId") Long businessId,
            Model model
    ) {
        Product product = new Product();
        model.addAttribute("businessId", businessId);
        model.addAttribute("product", product);
        model.addAttribute("allTags", tagService.listTags());
        return "product-form";
    }

    @PostMapping("/productSubmit")
    public String productSubmit(@ModelAttribute ProductDto product, Model model, Long businessId,
                                @RequestParam("imageFileOne") MultipartFile multipartFile) {
        log.info("{Product submit: } " + businessId);
        log.info("ProductDto {}", product );
        model.addAttribute("product", product);
        Optional<String> optionalUrl = CdnUtils.uploadFile(multipartFile);
        optionalUrl.ifPresent(product::setImageOneUrl);

        product.setProductSubject(businessService.getBusinessById(businessId));
        product.setProductSubjectId(businessId);

        List<Tag> savedTags = tagRepository.saveAll(product.getTagList());
        product.setTagList(savedTags);

        service.saveProduct(product);
        return "redirect:/businessList";
    }
    @PostMapping("/productUpdate/{id}")
    public String updateProduct(@ModelAttribute ProductDto product, @PathVariable long id){

        List<Tag> savedTags = tagRepository.saveAll(product.getTagList());
        product.setTagList(savedTags);

        service.updateProduct(product);
        return "redirect:/productPage/{id}";
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

    @GetMapping("/productList") //With filter
    public String findAllProducts(Model model, @Param("keyword") String keyword){
        model.addAttribute("product", service.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "product-list";
    }

//     UPDATE - PUT
    @GetMapping("/productEdit/{id}")
    public String editProduct(@PathVariable long id, Model model) {

//    model.addAttribute("allTags", tagService.listTags());

    Product product = new Product();
    product = service.getProductById(id);
    model.addAttribute("product", product);
    model.addAttribute("allTags", tagService.listTags());

        return "product-edit";
}

//    @PostMapping("/productUpdate/{id}")
//    public String updateProduct2(@ModelAttribute ProductDto product, @PathVariable long id){
//        service.updateProduct(product);
//        return "redirect:/productPage/{id}";
//    }


    // DELETE
    @GetMapping("/productDelete/{id}")
    public String deleteProduct(@PathVariable long id) {
        service.deleteProduct(id);
        return "redirect:/businessList";
    }


}
