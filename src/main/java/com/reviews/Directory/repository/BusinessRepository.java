package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findByBusinessName(String businessName);
}
