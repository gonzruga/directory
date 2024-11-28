package com.reviews.Directory.controller;

import com.reviews.Directory.dto.TransactionStatusRequest;
import com.reviews.Directory.dto.TransactionStatusResponse;
import com.reviews.Directory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class TransactionStatusController {

    private final PaymentService paymentService;

    // Transaction Status Enquiry Endpoint
//    @PostMapping("/status")
//    public ResponseEntity<TransactionStatusResponse> getTransactionStatus(
//            @RequestHeader("X-Client-Id") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody TransactionStatusRequest request) {
//
//        // Verify client credentials (Optional: Customize as per your authentication mechanism)
//        if (!paymentService.verifyClientCredentials(clientId, clientSecret)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        // Fetch and return transaction status
//        TransactionStatusResponse statusResponse = paymentService.fetchTransactionStatus(request.getTransactionId());
//        return ResponseEntity.ok(statusResponse);
//    }
    @PostMapping("/status")
    public ResponseEntity<?> getTransactionStatus(
            @RequestHeader("X-Client-Id") String clientId,
            @RequestHeader("X-Client-Secret") String clientSecret,
            @RequestBody TransactionStatusRequest request) {

        // Validate headers and credentials
        if (clientId == null || clientSecret == null) {
            return ResponseEntity.badRequest().body("Client ID and Secret are required.");
        }

        if (!paymentService.verifyClientCredentials(clientId, clientSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Client ID or Secret.");
        }

        // Validate transaction request payload
        if (request.getTransactionId() == null || request.getTransactionId().isEmpty()) {
            return ResponseEntity.badRequest().body("Transaction ID is required.");
        }

        try {
            // Fetch transaction status from the service
            TransactionStatusResponse statusResponse = paymentService.fetchTransactionStatus(request.getTransactionId());

            if (statusResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Transaction not found for ID: " + request.getTransactionId());
            }

            return ResponseEntity.ok(statusResponse);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }

}
