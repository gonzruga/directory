package com.reviews.Directory.utils;

import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@Slf4j
public class ProductUtils {

    private static final int SPONSOR_LEVEL_EXPIRY_DAYS = 30;

    /**
     * Checks if the product's payment date is more than 30 days old.
     * If so, sets the sponsor level to 0.
     *
     * @param product the product to check and update
     */
    public static Product updateSponsorLevelIfExpired(Product product) {
        if (product.getPaymentDate() != null && isPaymentDateExpired(product.getPaymentDate())) {
            product.setSponsorLevel(0);
        }
        return product;

    }

    /**
     * Helper method to determine if a given payment date is more than 30 days before today.
     *
     * @param paymentDate the date of the payment to check
     * @return true if payment date is more than 30 days old, false otherwise
     */
    private static boolean isPaymentDateExpired(Date paymentDate) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(SPONSOR_LEVEL_EXPIRY_DAYS);
        LocalDate paymentLocalDate = paymentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        log.info("thirtyDaysAgo {} paymentLocalDate {} paymentDate {} ", thirtyDaysAgo, paymentLocalDate, paymentDate);
        boolean before = paymentLocalDate.isBefore(thirtyDaysAgo);
        log.info("thirtyDaysAgo {} paymentLocalDate {} paymentDate {} is before  {}", thirtyDaysAgo, paymentLocalDate, paymentDate, before );

        return before;
    }

}
