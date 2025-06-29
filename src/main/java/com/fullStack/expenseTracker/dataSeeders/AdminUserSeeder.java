package com.fullStack.expenseTracker.dataSeeders;

import com.fullStack.expenseTracker.enums.ERole;
import com.fullStack.expenseTracker.models.Role;
import com.fullStack.expenseTracker.models.User;
import com.fullStack.expenseTracker.repository.RoleRepository;
import com.fullStack.expenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class AdminUserSeeder {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener
    @Transactional
    public void createAdminUser(ContextRefreshedEvent event) {
        // Check if admin user already exists
        if (userRepository.existsByEmail("admin@expensetracker.com")) {
            System.out.println("Admin user already exists!");
            return;
        }

        // Create admin user
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setEmail("admin@expensetracker.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setEnabled(true);
        adminUser.setVerificationCode("ADMIN_VERIFIED");
        adminUser.setVerificationCodeExpiryTime(new Date(System.currentTimeMillis() + 86400000)); // 24 hours

        // Set admin role
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found!"));
        roles.add(adminRole);
        adminUser.setRoles(roles);

        // Save admin user
        userRepository.save(adminUser);
        System.out.println("Admin user created successfully!");
        System.out.println("Email: admin@expensetracker.com");
        System.out.println("Password: admin123");
    }
} 