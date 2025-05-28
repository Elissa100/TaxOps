package com.rwanda.taxops.controller;

import com.rwanda.taxops.dto.TaxAssessmentDTO;
import com.rwanda.taxops.model.*;
import com.rwanda.taxops.repository.TaxAssessmentRepository;
import com.rwanda.taxops.repository.TaxCategoryRepository;
import com.rwanda.taxops.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tax-assessments")
public class TaxAssessmentController {

    @Autowired
    private TaxAssessmentRepository taxAssessmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaxCategoryRepository taxCategoryRepository;

    @PostMapping
    @PreAuthorize("hasRole('TAX_COLLECTOR')")
    public ResponseEntity<?> createTaxAssessment(@Valid @RequestBody TaxAssessmentDTO taxAssessmentDTO) {
        User taxpayer = userRepository.findById(taxAssessmentDTO.getTaxpayerId())
                .orElseThrow(() -> new RuntimeException("Error: Taxpayer not found."));

        TaxCategory taxCategory = taxCategoryRepository.findById(taxAssessmentDTO.getTaxCategoryId())
                .orElseThrow(() -> new RuntimeException("Error: Tax category not found."));

        TaxAssessment taxAssessment = new TaxAssessment();
        taxAssessment.setTaxpayer(taxpayer);
        taxAssessment.setTaxCategory(taxCategory);
        taxAssessment.setAmount(taxAssessmentDTO.getAmount());
        taxAssessment.setDueDate(taxAssessmentDTO.getDueDate());
        taxAssessment.setStatus(AssessmentStatus.PENDING);

        taxAssessmentRepository.save(taxAssessment);
        return ResponseEntity.ok("Tax assessment created successfully");
    }

    @GetMapping("/taxpayer/{taxpayerId}")
    @PreAuthorize("hasRole('TAX_COLLECTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<TaxAssessment>> getTaxAssessmentsByTaxpayer(@PathVariable Long taxpayerId) {
        User taxpayer = userRepository.findById(taxpayerId)
                .orElseThrow(() -> new RuntimeException("Error: Taxpayer not found."));

        return ResponseEntity.ok(taxAssessmentRepository.findByTaxpayer(taxpayer));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TAXPAYER')")
    public ResponseEntity<List<TaxAssessment>> getMyTaxAssessments() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(taxAssessmentRepository.findByTaxpayer(currentUser));
    }
}