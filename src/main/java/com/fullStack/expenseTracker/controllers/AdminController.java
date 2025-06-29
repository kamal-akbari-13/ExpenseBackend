package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> getAdminDashboard() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDto<>(
                        com.fullStack.expenseTracker.enums.ApiResponseStatus.SUCCESS,
                        HttpStatus.OK,
                        "Admin dashboard accessed successfully"
                )
        );
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> getAdminStats() {
        // You can add admin-specific statistics here
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDto<>(
                        com.fullStack.expenseTracker.enums.ApiResponseStatus.SUCCESS,
                        HttpStatus.OK,
                        "Admin statistics retrieved successfully"
                )
        );
    }
} 