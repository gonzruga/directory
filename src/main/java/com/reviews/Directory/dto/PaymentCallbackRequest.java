package com.reviews.Directory.dto;

import lombok.Data;

@Data
public class PaymentCallbackRequest {

    private String referenceId;
    private String status;
    private int amount;
    private String operator;
    private String trxDate;
}
