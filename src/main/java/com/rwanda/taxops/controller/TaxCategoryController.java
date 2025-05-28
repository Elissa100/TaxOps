package com.rwanda.taxops.controller;

import com.rwanda.taxops.dto.TaxCategoryDTO;
import com.rwanda.taxops.model.TaxCategory;
import com.rwanda.taxops.repository.TaxCategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tax-categories")
public class TaxCategoryController {

    @Autowired
    private TaxCategoryRepository taxCategoryRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTaxCategory(@Valid @RequestBody TaxCategoryDTO taxCategoryDTO) {
        if (taxCategoryRepository.existsByName(taxCategoryDTO.getName())) {
            return ResponseEntity.badRequest().body("Error: Tax category name is already taken!");
        }

        TaxCategory taxCategory = new TaxCategory();
        taxCategory.setName(taxCategoryDTO.getName());
        taxCategory.setRate(taxCategoryDTO.getRate());
        taxCategory.setPercentage(taxCategoryDTO.isPercentage());

        taxCategoryRepository.save(taxCategory);
        return ResponseEntity.ok("Tax category created successfully");
    }

    @GetMapping
    public ResponseEntity<List<TaxCategory>> getAllTaxCategories() {
        return ResponseEntity.ok(taxCategoryRepository.findAll());
    }
}