package com.rwanda.taxops.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxAssessmentDTO {
    @NotNull
    private Long taxpayerId;

    @NotNull
    private Long taxCategoryId;

    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    private LocalDate dueDate;

    public Long getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(Long taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}