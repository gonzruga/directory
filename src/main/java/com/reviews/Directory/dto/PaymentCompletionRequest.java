package com.reviews.Directory.dto;

import lombok.Data;

@Data
public class PaymentCompletionRequest {

    private String msisdn;
    private int amount;
    private String receipt;
    private String signature;
    private String operator;
    private String status;
    private String orderId;
    private String transactionId;
    private String statusDesc;
}
