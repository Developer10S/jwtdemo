package com.jwtexample.jwtdemo.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//metodo que genera el jwt
//metodo que valida el jwt
//metodo que check expiryof jwt

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) { //EXTRA EL NOMBREUSER DEL TOKEN
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) { //EXTRAE EL TIEMPO DE CADUCIDAD DEL TOKEN
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //EXTRAE LA CARGA --- BODY
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) { //EXTRAE LOS RECLAMOS
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) { //VERIFICAR SI EL TOKEN YA EXPIRÓ
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) { //TOKEN
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) { //CREACION DEL TOKEN

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) { //VALIDA SI EL TOKEN ES VALIDO
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}