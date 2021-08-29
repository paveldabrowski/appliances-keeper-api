package io.applianceskeeper.auth.controller;

import io.applianceskeeper.auth.models.JwtResponse;
import io.applianceskeeper.auth.models.LoginRequest;
import io.applianceskeeper.auth.models.MessageResponse;
import io.applianceskeeper.auth.models.SignupRequest;
import io.applianceskeeper.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @GetMapping
    public ResponseEntity<Boolean> checkIfTokenValid(HttpServletRequest request) {
        log.info("Token is being verifying.");
        var isTokenValid = authService.validateToken(request);
        if (isTokenValid) {
            log.info("Token is valid.");
        } else {
            log.error("Token is inValid.");
        }
        return ResponseEntity.ok(isTokenValid);
    }
}
