package com.rwanda.taxops.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('TAXPAYER') or hasAuthority('TAX_COLLECTOR') or hasAuthority('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/taxpayer")
    @PreAuthorize("hasAuthority('TAXPAYER')")
    public String taxpayerAccess() {
        return "Taxpayer Board.";
    }

    @GetMapping("/taxcollector")
    @PreAuthorize("hasAuthority('TAX_COLLECTOR')")
    public String taxCollectorAccess() {
        return "Tax Collector Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}