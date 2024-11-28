package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
