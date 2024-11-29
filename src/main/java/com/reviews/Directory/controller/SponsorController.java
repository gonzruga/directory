package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.dto.SponsorDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Sponsor;
import com.reviews.Directory.service.ProductService;
import com.reviews.Directory.service.SponsorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//@RestController
//@RequestMapping("/sponsorSubmit")
//@RequestMapping(value = "/sponsorSubmit", method = {RequestMethod.GET, RequestMethod.POST})
@Slf4j
@Controller
@RequiredArgsConstructor
public class SponsorController {


    @Autowired
    private final SponsorService service;

    @Autowired
    private final ProductService productService;

    @GetMapping("sponsorForm/{productId}")
    public String sponsorCreate(
            @PathVariable(name="productId") Long productId, Model model, @Param("sponsorLevel") int sponsorLevel){
        Sponsor sponsor = new Sponsor();
        model.addAttribute("productId", productId);
        model.addAttribute("sponsor", sponsor);
        return "sponsor-form";
    }

    @PostMapping("/sponsorSubmit")
    public String sponsorSubmit(
            @RequestParam("sponsorLevel") int sponsorLevel,
            @RequestParam("productId") Long productId,
            Model model) {

        // Retrieve product by ID
        var product = productService.getProductById(productId);

        // Calculate fee based on sponsor level
        double fee = calculateFee(sponsorLevel);

        // Create SponsorDto and set its fields
        SponsorDto sponsor = new SponsorDto();
        sponsor.setSponsorLevel(sponsorLevel);
        sponsor.setFee(fee); // Set the fee
        sponsor.setProduct(product);
        sponsor.setProductId(productId);

        // Save sponsor
        service.saveSponsor(sponsor);

        // Add attributes to model if needed
        model.addAttribute("sponsor", sponsor);

        // Redirect to the product list page
        return "redirect:/productList";
    }

    private double calculateFee(int sponsorLevel) {
        switch (sponsorLevel) {
            case 1: return 500;
            case 2: return 600;
            case 3: return 700;
            case 4: return 800;
            case 5: return 900;
            case 6: return 1000;
            case 7: return 1100;
            case 8: return 1200;
            case 9: return 1300;
            default: throw new IllegalArgumentException("Invalid sponsor level: " + sponsorLevel);
        }
    }

    @PostMapping("/updateSponsorPayment")
    public String updateSponsorPayment(
            @RequestParam("sponsorId") Long sponsorId,
            Model model) {
        // Retrieve the sponsor
        Sponsor sponsor = service.updateSponsorPayment(sponsorId);
//        Sponsor sponsor = service.getSponsorById(sponsorId);
        if (sponsor == null) {
            model.addAttribute("error", "Sponsor not found.");
            return "errorPage"; // Redirect to an error page or handle appropriately
        }

        // Retrieve the product by productId
        Product product = productService.getProductById(sponsor.getProductId());
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "errorPage"; // Redirect to an error page or handle appropriately
        }

        // Update the product's sponsorLevel and paymentDate
        product.setSponsorLevel(sponsor.getSponsorLevel());
        product.setPaymentDate(new Date()); // Update with the current date

        // Save the updated product
        productService.acceptProduct(product);

        // Add attributes for confirmation, if needed
        model.addAttribute("product", product);
        model.addAttribute("message", "Sponsor payment processed successfully.");

        // Redirect or return a view
        return "redirect:/productList"; // Adjust as needed for your flow
    }

    @PostMapping("/processSponsorPayment")
    public String processSponsorPayment(
            @RequestParam("sponsorId") Long sponsorId,
            Model model) {

        // Retrieve sponsor by ID
        Sponsor sponsor = service.getSponsorById(sponsorId);
        if (sponsor == null) {
            model.addAttribute("error", "Sponsor not found.");
            return "errorPage"; // Ensure this template exists
        }

        // Retrieve product ID from sponsor
        Long productId = sponsor.getProduct() != null ? sponsor.getProduct().getId() : null;
        if (productId == null) {
            model.addAttribute("error", "Product ID is missing for the sponsor.");
            return "errorPage";
        }

        // Retrieve product by ID
        Product product = productService.getProductById(productId);
        if (product == null) {
            model.addAttribute("error", "Product not found for the given sponsor.");
            return "errorPage";
        }

        // Update product's sponsor level and payment date
        product.setSponsorLevel(sponsor.getSponsorLevel());
        product.setPaymentDate(new Date());

        // Save updated product
        productService.acceptProduct(product);

        // Add success message and redirect to product list
        model.addAttribute("message", "Sponsor payment processed successfully. Product updated.");
        return "redirect:/productList";
    }


}
