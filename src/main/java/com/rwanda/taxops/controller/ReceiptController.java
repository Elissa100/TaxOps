package com.rwanda.taxops.controller;

import com.rwanda.taxops.dto.ReceiptDTO;
import com.rwanda.taxops.model.Payment;
import com.rwanda.taxops.model.Receipt;
import com.rwanda.taxops.repository.PaymentRepository;
import com.rwanda.taxops.repository.ReceiptRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    @PreAuthorize("hasRole('TAX_COLLECTOR')")
    public ResponseEntity<?> createReceipt(@Valid @RequestBody ReceiptDTO receiptDTO) {
        Payment payment = paymentRepository.findById(receiptDTO.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getReceipt() != null) {
            return ResponseEntity.badRequest().body("Payment already has a receipt");
        }

        Receipt receipt = new Receipt();
        receipt.setReceiptNumber(receiptDTO.getReceiptNumber());
        receipt.setPayment(payment);
        receipt.setIssueDate(receiptDTO.getIssueDate());

        receiptRepository.save(receipt);
        return ResponseEntity.ok(receipt);
    }
}