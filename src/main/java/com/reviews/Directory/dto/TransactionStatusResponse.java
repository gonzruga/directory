package com.reviews.Directory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStatusResponse {
    private String transactionId;
    private String status;
    private String description;
}
