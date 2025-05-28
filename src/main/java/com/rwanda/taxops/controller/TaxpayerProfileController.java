package com.rwanda.taxops.controller;

import com.rwanda.taxops.dto.TaxpayerProfileDTO;
import com.rwanda.taxops.model.TaxpayerProfile;
import com.rwanda.taxops.model.User;
import com.rwanda.taxops.repository.TaxpayerProfileRepository;
import com.rwanda.taxops.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/taxpayer-profiles")
public class TaxpayerProfileController {

    @Autowired
    private TaxpayerProfileRepository taxpayerProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('TAXPAYER')")
    public ResponseEntity<?> createTaxpayerProfile(
            @Valid @RequestBody TaxpayerProfileDTO taxpayerProfileDTO,
            Principal principal) {

        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (taxpayerProfileRepository.existsByUser(currentUser)) {
            return ResponseEntity.badRequest().body("User already has a profile");
        }

        TaxpayerProfile profile = new TaxpayerProfile();
        profile.setNid(taxpayerProfileDTO.getNid());
        profile.setPhone(taxpayerProfileDTO.getPhone());
        profile.setAddress(taxpayerProfileDTO.getAddress());
        profile.setUser(currentUser);

        taxpayerProfileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TAXPAYER')")
    public ResponseEntity<?> getMyTaxpayerProfile(Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taxpayerProfileRepository.findByUser(currentUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}