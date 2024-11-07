package com.project.Citas.services;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String SECRET_KEY = "secret"; // Cambia esto en producción
    private final long EXPIRATION_TIME = 864_000_000; 

    public String generateToken(String username,long tipoUsuario,Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tipoUsuario", tipoUsuario); 
        claims.put("id",id);
        return Jwts.builder()
                .setClaims(claims) 
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
