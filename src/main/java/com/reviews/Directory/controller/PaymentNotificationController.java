package com.reviews.Directory.controller;

import com.reviews.Directory.dto.PaymentCompletionRequest;
import com.reviews.Directory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentNotificationController {

    private final PaymentService paymentService;

    // Payment Completion Notification Endpoint
    @PostMapping("/notify")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentCompletionRequest request) {
        // Verify signature
        boolean isVerified = paymentService.verifyNotificationSignature(request);

        if (isVerified) {
            // Process the transaction status (Completed, Failed, etc.)
            paymentService.processTransactionStatus(request);
            return ResponseEntity.ok("Notification received and processed.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature.");
        }
    }

}
