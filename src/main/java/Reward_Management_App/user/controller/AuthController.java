package Reward_Management_App.user.controller;

import Reward_Management_App.user.dto.request.LoginRequestDto;
import Reward_Management_App.user.dto.response.LoginResponseDto;
import Reward_Management_App.user.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserAuthService userAuthService;
    public AuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto loginResponse = userAuthService.userLogin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
