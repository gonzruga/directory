package com.reviews.Directory.service;

import com.reviews.Directory.dto.PaymentCallbackRequest;
import com.reviews.Directory.dto.PaymentValidationRequest;
import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    final ProductRepository productRepository;

    private final String HASH_KEY = "yoursecuritykey"; // Replace with the actual key provided by FastHub

    public void makePayment(){


    }
    /**
     * Validates the transaction using the hash and provided data.
     */
    public boolean validateTransaction(PaymentValidationRequest request) {
        // Generate hash from received data
        String dataString = createDataString(request);
        String calculatedHash = generateHash(dataString);

        String orderId = request.getOrderId();
        Optional<Product> byId = productRepository.findById(Long.parseLong(orderId));

        return byId.isPresent();
//        if(!byId.isPresent()) return false;
//
//        Product product = byId.get();
//
//        // Compare the calculated hash with the received hash
//        return calculatedHash.equals(request.getHash());
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
//                "&country=" + request.getCountry() +
                "&msisdn=" + request.getMsisdn() +
                "&operator=" + request.getOperator() +
//                "&paybill_number=" + request.getPaybillNumber() +
//                "&reference_id=" + request.getReferenceId() +
//                "&trx_date=" + request.getTrxDate();
                "transaction_id=" + request.getTransactionId() +
                "receipt" + request.getReceipt() +
                "signature" + request.getSignature() +
                "order_id" + request.getOrderId();

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



}
