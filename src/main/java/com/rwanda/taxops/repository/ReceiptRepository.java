package com.rwanda.taxops.repository;

import com.rwanda.taxops.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    boolean existsByReceiptNumber(String receiptNumber);
}