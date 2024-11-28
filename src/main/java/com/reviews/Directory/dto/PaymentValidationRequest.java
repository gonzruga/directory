package com.reviews.Directory.dto;

import lombok.Data;

@Data
public class PaymentValidationRequest {
    private String hash;
    private String trxDate;
    private String paybillNumber;
    private String referenceId;
    private String msisdn;
    private int amount;
    private String operator;
    private String country;
}
