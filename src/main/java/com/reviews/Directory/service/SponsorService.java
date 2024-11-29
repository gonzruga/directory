package com.reviews.Directory.service;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.dto.SponsorDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Sponsor;
import com.reviews.Directory.repository.SponsorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SponsorService {
    private final ProductService productService;
    private final SponsorRepository repository;

    public Sponsor saveSponsor(SponsorDto sponsor) {
        return repository.save(sponsor.dtoToSponsor());
    }

    public Sponsor getSponsorById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Sponsor updateSponsorPayment(Long sponsorId) {
        Sponsor sponsor = getSponsorById(sponsorId);

        if (sponsor == null) {
            return null;
        }

        Product product = productService.getProductById(sponsor.getProductId());
        if (product == null) {
            return null; // Redirect to an error page or handle appropriately
        }

        product.setSponsorLevel(sponsor.getSponsorLevel());
        product.setPaymentDate(new Date());

        productService.acceptProduct(product);

        return sponsor;
    }
}
