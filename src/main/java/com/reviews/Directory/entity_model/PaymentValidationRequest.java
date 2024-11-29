package com.reviews.Directory.entity_model;

import com.reviews.Directory.dto.PaymentValidationRequestDTO;
import lombok.Data;

@Data
public class PaymentValidationRequest {
    private String hash;
    private String trxDate;
    private String paybill_number;
    private String reference_Id;
    private String msisdn;
    private int amount;
    private String operator;
    private String country;

    public static PaymentValidationRequest from (PaymentValidationRequestDTO paymentValidationRequestDTO){
        PaymentValidationRequest paymentValidationRequest = new PaymentValidationRequest();
        paymentValidationRequest.setAmount(paymentValidationRequestDTO.getAmount());
        paymentValidationRequest.setHash(paymentValidationRequestDTO.getHash());
        paymentValidationRequest.setPaybill_number(paymentValidationRequestDTO.getPaybillNumber());
        paymentValidationRequest.setTrxDate(paymentValidationRequestDTO.getTrxDate());
        paymentValidationRequest.setReference_Id(paymentValidationRequestDTO.getReferenceId());
        paymentValidationRequest.setOperator(paymentValidationRequestDTO.getOperator());
        paymentValidationRequest.setCountry(paymentValidationRequestDTO.getCountry());
        return  paymentValidationRequest;
    }

}
