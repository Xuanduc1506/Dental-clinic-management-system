package com.example.dentalclinicmanagementsystem.security;

import com.example.dentalclinicmanagementsystem.dto.TokenDTO;
import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.entity.Role;
import com.example.dentalclinicmanagementsystem.entity.User;
import com.example.dentalclinicmanagementsystem.exception.TokenException;
import com.example.dentalclinicmanagementsystem.repository.RoleRepository;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final String jwtSecret = "dental_clinic";

    private final long JWT_EXPIRATION = 604800000L;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public TokenDTO createToken(Authentication authentication){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        Role role = roleRepository.findRoleNameByUser(authentication.getName());

        User user = userRepository.findUsersByUserName(authentication.getName());
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setJwt(Jwts.builder().setSubject(authentication.getName()).setId(user.getUserId().toString()).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).setExpiration(expiryDate)
                .compact());
        tokenDTO.setRole(role.getRoleName());

        return tokenDTO;
    }

    public String getAccountFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new TokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new TokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new TokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new TokenException("JWT claims string is empty.");
        }
    }
}
