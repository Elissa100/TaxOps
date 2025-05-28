package com.rwanda.taxops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taxpayer_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nid")
        })
public class TaxpayerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}