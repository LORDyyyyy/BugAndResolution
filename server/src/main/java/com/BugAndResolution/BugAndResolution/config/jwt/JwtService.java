package com.BugAndResolution.BugAndResolution.config.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "2e6c4906e8674a837f07b5d5d2e9eaff4a7a9a09edcc3a63bbc4e9ab847085ae24102da9e3feb9c82a0f9db26f635adfcc383032e55ba94dea2c5def6452d9a5e939c34ea6b8ba2a453bc8cc90ade5a5115696275ade68cdd063e59b8ca86eb8339eca095bf62adf802790180af4f385f275aaf5ff40507f2fe414878d6a2bd526c557d661e78a3ae0603b9a76b7c456160ff363bb9092b2c79827f5645f5edce0f6899e7269d37a56a318e2654dee9ce1102819f0faa5567d7d6689a54e33992d59427c6ec3d4d41f5ad13f0673f4e3db727e0bd53c675e1d535ee76b2100f2ef0acf226322761571ccf6eba72a69ee0ea3a5d287f960b6c25509c194139144";
    
    private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 24; // 1 day

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList());
                
        return generateToken(claims, userDetails, ACCESS_TOKEN_VALIDITY);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expirationTime
    ) {
        Map<String, Object> header = new HashMap<>();
        header.put("type", "jwt");
        
        return Jwts
            .builder()
            .setHeader(header)
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
        
    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
                
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
        
    private Claims extractAllClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
        
    private Key getSigningKey() {
        byte[] keyBytes = HexFormat.of().parseHex(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}