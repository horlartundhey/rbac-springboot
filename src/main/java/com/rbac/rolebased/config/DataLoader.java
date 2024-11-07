package com.rbac.rolebased.config;

import com.rbac.rolebased.entities.Role;
import com.rbac.rolebased.entities.User;
import com.rbac.rolebased.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner loadInitialData(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (!userRepository.findByEmail("admin@example.com").isPresent()) {
                User admin = new User();
                admin.setFirstname("Admin");
                admin.setLastname("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);

                userRepository.save(admin);

            }
            if (!userRepository.findByEmail("user@example.com").isPresent()) {
                User instructor = new User();
                instructor.setFirstname("Instructor");
                instructor.setLastname("instuctoor");
                instructor.setEmail("user@example.com");
                instructor.setPassword(passwordEncoder.encode("instruct123"));
                instructor.setRole(Role.INSTRUCTOR);
                instructor.setEnabled(true);

                userRepository.save(instructor);

            }

        };
    }

}
