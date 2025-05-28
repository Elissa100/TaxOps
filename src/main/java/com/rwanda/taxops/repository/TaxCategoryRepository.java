package com.rwanda.taxops.repository;

import com.rwanda.taxops.model.TaxCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Long> {
    boolean existsByName(String name);
}