package com.reviews.Directory.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class PaymentValidationRequest {
    private String hash;
    private String trxDate;
//    @JsonAlias("transaction_id")
//    private String transactionId;
    private String paybillNumber;
    private String referenceId;
    private String msisdn;
    private int amount;
    private String operator;
    private String country;
//    private String receipt;
//    private String signature;
//    @JsonAlias("order_id")
//    private String orderId;
}
