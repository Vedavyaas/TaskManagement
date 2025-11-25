package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Assests.JWTResponse;
import com.amdox.taskmanagement.Assests.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JWTAuthenticationController {

    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationController(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    ResponseEntity<JWTResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        String token = createToken(authentication);
        return ResponseEntity.ok(new JWTResponse(token));
    }

    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now()).expiresAt(Instant.now().plusSeconds(60 * 10)).subject(authentication.getName()).claim("scope", createScope(authentication)).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }
}

