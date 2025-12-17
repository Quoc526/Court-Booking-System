package com.example.booking.controller;

import com.example.booking.dto.ApiResponse;
import com.example.booking.dto.RegisterRequestDTO;
import com.example.booking.dto.UserResponseDTO;
import com.example.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        UserResponseDTO user = userService.registerUser(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("User registered successfully", user));
    }
}
