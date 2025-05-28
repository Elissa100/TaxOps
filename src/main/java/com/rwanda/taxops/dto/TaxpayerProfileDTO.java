package com.rwanda.taxops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaxpayerProfileDTO {
    @NotBlank
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[0-9]*[A-Za-z][0-9]*$", message = "NID must be in valid Rwandan format")
    private String nid;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotBlank
    @Size(max = 200)
    private String address;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}