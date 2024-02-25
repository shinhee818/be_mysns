package com.sini.mysns.global.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Log4j2
@Component
public class TokenProvider {

    private final Key jwtSecretKey;

    public TokenProvider(@Value("${jwt.secret-key}") String secretKey)
    {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        this.jwtSecretKey = Keys.hmacShaKeyFor(decode);
    }

    public String generateAccessToken(MemberDTO memberDTO)
    {
        Map<String, Object> claims = memberDTO.getClaims();
        return generateToken(claims, 60 * 24 * 365 * 100);
    }

    public String generateRefreshToken(MemberDTO memberDTO)
    {
        Map<String, Object> claims = memberDTO.getClaims();
        return generateToken(claims, 60 * 24);
    }

    public String generateToken(Map<String, Object> valueMap, int min)
    {
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
//                .setSubject("authorization")
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> validateToken(String token)
    {
        Map<String, Object> claim = null;
        try
        {
            claim = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        }
        catch(MalformedJwtException malformedJwtException)
        {
            log.error("malformedJwtException ...");
            throw new CustomJWTException("MalFormed");
        }
        catch(ExpiredJwtException expiredJwtException)
        {
            log.error("expiredJwtException ...");
            throw new CustomJWTException("Expired");
        }
        catch(InvalidClaimException invalidClaimException)
        {
            log.error("invalidClaimException ...");
            throw new CustomJWTException("Invalid");
        }
        catch(JwtException jwtException)
        {
            log.error("jwtException ...");
            throw new CustomJWTException("JWTError");
        }
        catch(Exception e)
        {
            log.error("Exception ...");
            throw new CustomJWTException("Error");
        }
        return claim;
    }
}
