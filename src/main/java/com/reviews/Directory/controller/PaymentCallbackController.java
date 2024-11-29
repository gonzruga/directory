package com.reviews.Directory.controller;

import com.reviews.Directory.dto.C2BCallbackResponseDto;
import com.reviews.Directory.dto.PaymentCallbackRequest;
import com.reviews.Directory.entity_model.PaymentValidationRequest;
import com.reviews.Directory.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentCallbackController {

    private final PaymentService paymentService;

    // Endpoint to handle validation requests
    @PostMapping("/validate")
    public Map<String, Object> validatePayment(@RequestBody PaymentValidationRequest request) {
        log.info("The request received is {}", request);

        // Verify signature

        boolean isValid = paymentService.validateTransaction(request);
        Map<String, Object> response = new HashMap<>();

        if (isValid) {
            response.put("code", 200);
            response.put("status", "ok");
            response.put("reference_id", request.getReference_Id());
        } else {
            response.put("code", 404);
            response.put("status", "not found");
            response.put("reference_id", request.getReference_Id());
        }
        log.info("The request is: {}", response);
        return (response);
    }

    // Callback URL endpoint
    @PostMapping("/callback")
    public C2BCallbackResponseDto handleCallback(@RequestBody PaymentCallbackRequest request) {
        paymentService.processCallback(request);
//        return ("Callback received and processed successfully.");
        C2BCallbackResponseDto responseDt = new C2BCallbackResponseDto();
        responseDt.setStatus_code("200");
        responseDt.setStatus_desc("ok");
        return responseDt;
    }

}
