package com.reviews.Directory.service;

import com.reviews.Directory.dto.ProductDto;
import com.reviews.Directory.dto.SponsorDto;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Sponsor;
import com.reviews.Directory.repository.SponsorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SponsorService {

private final SponsorRepository repository;

    public Sponsor saveSponsor(SponsorDto sponsor) {
        return repository.save(sponsor.dtoToSponsor());
    }

    public Sponsor getSponsorById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
