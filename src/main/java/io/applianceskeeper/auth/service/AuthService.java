package io.applianceskeeper.auth.service;

import io.applianceskeeper.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    public boolean validateToken(HttpServletRequest request) {
        var jwt = jwtUtils.parseJwt(request);
        return jwt.filter(jwtUtils::validateJwtToken).isPresent();
    }
}
