package com.reviews.Directory.service;

import com.reviews.Directory.dto.PaymentCompletionRequest;
import com.reviews.Directory.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final String HASH_KEY = "b5e688589a75bd8a62b1b6562e64aa20";

    /**
     * Validates the transaction using the hash and provided data.
     */
    public boolean validateTransaction(PaymentValidationRequest request) {
        // Generate hash from received data
        String dataString = createDataString(request);
        String calculatedHash = generateHash(dataString);

        // Compare the calculated hash with the received hash
        return calculatedHash.equals(request.getHash());
    }

    /**
     * Processes the callback from FastHub for payment completion.
     */
    public void processCallback(PaymentCallbackRequest request) {
        // TODO: Add your business logic for handling the payment callback
        // e.g., updating payment records, notifying the user, etc.
        System.out.println("Callback received for transaction: " + request.getReferenceId());
    }

    /**
     * Creates the data string in alphabetical order of parameters.
     */
    private String createDataString(PaymentValidationRequest request) {
        return "amount=" + request.getAmount() +
                "&country=" + request.getCountry() +
                "&msisdn=" + request.getMsisdn() +
                "&operator=" + request.getOperator() +
                "&paybill_number=" + request.getPaybillNumber() +
                "&reference_id=" + request.getReferenceId() +
                "&trx_date=" + request.getTrxDate();
    }

    /**
     * Generates HMAC-SHA256 hash for the given data string and key.
     */
    private String generateHash(String dataString) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(HASH_KEY.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            byte[] hashBytes = mac.doFinal(dataString.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }



    /*
    private final String SIGNATURE_KEY = "yourSignatureKey"; // Replace with actual key provided by FastHub

    // Verify signature for payment validation
    public boolean verifyValidationSignature(PaymentValidationRequest request) {
        String stringToHash = request.getAmount() + request.getMsisdn() +
                request.getOperator() + request.getOrderId();
        String calculatedSignature = generateSignature(stringToHash);
        return calculatedSignature.equals(request.getSignature());
    }

    // Verify signature for payment notification
    public boolean verifyNotificationSignature(PaymentCompletionRequest request) {
        String stringToHash = request.getAmount() + request.getMsisdn() +
                request.getOperator() + request.getOrderId() +
                request.getStatus();
        String calculatedSignature = generateSignature(stringToHash);
        return calculatedSignature.equals(request.getSignature());
    }

    // Process transaction status update from notification
    public void processTransactionStatus(PaymentCompletionRequest request) {
        // TODO: Add logic to update the transaction status in your system

        System.out.println("Processing transaction: " + request.getTransactionId());
        System.out.println("Status: " + request.getStatus());
    }

    // Generate signature using HMAC-SHA512
    private String generateSignature(String stringToHash) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(SIGNATURE_KEY.getBytes(), "HmacSHA512");
            mac.init(secretKey);
            byte[] hashBytes = mac.doFinal(stringToHash.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }

    // Verify client credentials (optional for status enquiry)
    public boolean verifyClientCredentials(String clientId, String clientSecret) {
        // TODO: Implement your own client verification logic
        return "expectedClientId".equals(clientId) && "expectedClientSecret".equals(clientSecret);
    }

    // Fetch transaction status (mocked example)
    public TransactionStatusResponse fetchTransactionStatus(String transactionId) {
        // TODO: Replace with actual implementation to query transaction status
        return new TransactionStatusResponse(transactionId, "Completed", "Transaction was successful.");
    }

     */
}
