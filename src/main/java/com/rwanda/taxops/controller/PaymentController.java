package com.rwanda.taxops.controller;

import com.rwanda.taxops.dto.PaymentDTO;
import com.rwanda.taxops.model.*;
import com.rwanda.taxops.repository.PaymentRepository;
import com.rwanda.taxops.repository.TaxAssessmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TaxAssessmentRepository taxAssessmentRepository;

    @PostMapping
    @PreAuthorize("hasRole('TAXPAYER')")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        TaxAssessment assessment = taxAssessmentRepository.findById(paymentDTO.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Error: Tax assessment not found."));

        // Check if the assessment already has a payment
        if (assessment.getPayment() != null) {
            return ResponseEntity.badRequest().body("Error: This assessment has already been paid");
        }

        Payment payment = new Payment();
        payment.setAssessment(assessment);
        payment.setAmountPaid(paymentDTO.getAmountPaid());
        payment.setDatePaid(LocalDateTime.now());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentDTO.getPaymentMethod()));

        // Update assessment status
        assessment.setStatus(AssessmentStatus.PAID);
        assessment.setPayment(payment);

        paymentRepository.save(payment);
        taxAssessmentRepository.save(assessment);

        return ResponseEntity.ok("Payment created successfully");
    }
}