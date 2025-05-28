package com.rwanda.taxops.repository;

import com.rwanda.taxops.model.TaxAssessment;
import com.rwanda.taxops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxAssessmentRepository extends JpaRepository<TaxAssessment, Long> {
    List<TaxAssessment> findByTaxpayer(User taxpayer);
}