package com.reviews.Directory.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class PaymentCallbackRequest {
    private String hash;
    private String trxId;
    private String company;
    private String paybillNumber;
    private String receipt;

//    @NotNull()
    private String referenceId;
//    @NotNull()
    private String status;
//    @NotNull()
    private Integer amount;
//    @NotNull()
    private String operator;
//    @NotNull()
    private String trxDate;
}
