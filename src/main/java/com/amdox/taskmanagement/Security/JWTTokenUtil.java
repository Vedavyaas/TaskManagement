package com.amdox.taskmanagement.Security;

import com.amdox.taskmanagement.Entity.UserAuthenticate;
import com.amdox.taskmanagement.Enum.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTTokenUtil {

    private final Key key;
    private final long expireToken = 1000L * 60 * 60 * 24;

    public JWTTokenUtil() {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.isEmpty()) {
            // In production, this should always be an environment variable
            secret = "ReplaceThisWithAVerySecretKeyForTaskManagementSystem"; 
        }
        // Ensure key length is sufficient for HS256
        if(secret.length() < 32) {
             secret = "ReplaceThisWithAVerySecretKeyForTaskManagementSystem"; 
        }
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserAuthenticate user) {
        Set<Permission> permissions = PermissionConfig.getPermission().get(user.getRole());
        
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireToken);

        return Jwts.builder()
                .setSubject(user.getUserOfficialEmail())
                .claim("role", user.getRole().name())
                .claim("permission", permissions != null ? permissions.stream().map(Enum::name).collect(Collectors.toList()) : java.util.Collections.emptyList())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public Claims getClaim(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
