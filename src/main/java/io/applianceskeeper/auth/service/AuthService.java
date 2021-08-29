package io.applianceskeeper.auth.service;

import io.applianceskeeper.auth.data.UserRepository;
import io.applianceskeeper.auth.models.*;
import io.applianceskeeper.security.JwtUtils;
import io.applianceskeeper.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final String ROLE_ERROR_MESSAGE = "Error: Role is not found.";
    private final PasswordEncoder encoder;
    private final RoleService roleService;


    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
        if (existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        var user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set<Role> roles = assignRoles(signUpRequest);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private Set<Role> assignRoles(SignupRequest signUpRequest) {
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = signUpRequest.getRoles();

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ROLE_ERROR_MESSAGE));
            roles.add(userRole);
        } else {
            convertRolesToSpringTypes(strRoles, roles);
        }
        return roles;
    }

    private void convertRolesToSpringTypes(Set<String> strRoles, Set<Role> roles) {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(ROLE_ERROR_MESSAGE));
                    roles.add(adminRole);

                    break;
                case "mod":
                    Role modRole = roleService.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException(ROLE_ERROR_MESSAGE));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleService.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(ROLE_ERROR_MESSAGE));
                    roles.add(userRole);
            }
        });
    }

    public boolean validateToken(HttpServletRequest request) {
        var jwt = jwtUtils.parseJwt(request);
        return jwt.filter(jwtUtils::validateJwtToken).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
