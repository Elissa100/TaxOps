package com.rwanda.taxops.repository;

import com.rwanda.taxops.model.TaxpayerProfile;
import com.rwanda.taxops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxpayerProfileRepository extends JpaRepository<TaxpayerProfile, Long> {
    Optional<TaxpayerProfile> findByUser(User user);
    boolean existsByUser(User user);
    boolean existsByNid(String nid);
}