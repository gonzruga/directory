package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findByReference(String referenceId);
}
