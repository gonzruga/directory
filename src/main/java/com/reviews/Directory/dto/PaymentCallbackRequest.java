package com.reviews.Directory.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
public class PaymentCallbackRequest {
//    private String hash;
//    private String trxId;
//    private String company;
//    private String paybillNumber;
//    private String receipt;
//
//    private String referenceId;
//    private String status;
//    private Integer amount;
//    private String operator;
//    private String trxDate;

        private String hash;
        private Date trx_date;
        private String company;
        private String operator;
//        private String transaction_id;
        private String trxid;
        private String paybill_number;
        private String msisdn;
        private int amount;
        private String reference;
        private String country;



}
