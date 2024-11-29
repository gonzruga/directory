package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.PaymentValidationRequest;
import lombok.Data;

@Data
public class PaymentValidationRequestDTO {
    private String hash;
    private String trxDate;
    private String paybillNumber;
    private String referenceId;
    private String msisdn;
    private int amount;
    private String operator;
    private String country;

    public static PaymentValidationRequestDTO from (PaymentValidationRequest paymentValidationRequest){
        PaymentValidationRequestDTO paymentValidationRequestDTO = new PaymentValidationRequestDTO();
        paymentValidationRequestDTO.setMsisdn(paymentValidationRequest.getMsisdn());
        paymentValidationRequestDTO.setPaybillNumber(paymentValidationRequest.getPaybill_number());
        paymentValidationRequestDTO.setTrxDate(paymentValidationRequest.getTrxDate());
        paymentValidationRequestDTO.setHash(paymentValidationRequest.getHash());
        paymentValidationRequestDTO.setReferenceId(paymentValidationRequest.getReference_Id());
        paymentValidationRequestDTO.setAmount(paymentValidationRequest.getAmount());
        paymentValidationRequestDTO.setOperator(paymentValidationRequest.getOperator());
        paymentValidationRequestDTO.setCountry(paymentValidationRequest.getCountry());
        return  paymentValidationRequestDTO;
    }
}
