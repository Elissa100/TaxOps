package com.rwanda.taxops;

import com.rwanda.taxops.model.Role;
import com.rwanda.taxops.model.User;
import com.rwanda.taxops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaxOpsApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TaxOpsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByEmail("admin@taxops.gov.rw")) {
            User admin = new User(
                    "Admin",
                    "admin@taxops.gov.rw",
                    passwordEncoder.encode("Admin@1234"),
                    Role.ADMIN
            );
            userRepository.save(admin);
        }
    }
}