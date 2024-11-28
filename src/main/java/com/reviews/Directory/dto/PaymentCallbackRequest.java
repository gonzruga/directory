package com.reviews.Directory.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class PaymentCallbackRequest {
    @NotNull()
    private String referenceId;
    @NotNull()
    private String status;
    @NotNull()
    private Integer amount;
    @NotNull()
    private String operator;
    @NotNull()
    private String trxDate;
}
