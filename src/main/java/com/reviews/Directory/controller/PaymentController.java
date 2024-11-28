package com.reviews.Directory.controller;

import com.reviews.Directory.dto.PaymentCallbackRequest;
import com.reviews.Directory.dto.PaymentValidationRequest;
import com.reviews.Directory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Endpoint to handle validation requests
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validatePayment(@RequestBody PaymentValidationRequest request) {
        boolean isValid = paymentService.validateTransaction(request);
        Map<String, Object> response = new HashMap<>();

        if (isValid) {
            response.put("code", 200);
            response.put("status", "ok");
            response.put("reference_id", request.getReferenceId());
        } else {
            response.put("code", 400);
            response.put("status", "failed");
            response.put("reference_id", request.getReferenceId());
        }

        return ResponseEntity.ok(response);
    }

    // Callback URL endpoint
    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody PaymentCallbackRequest request) {
        paymentService.processCallback(request);
        return ResponseEntity.ok("Callback received and processed successfully.");
    }


}
