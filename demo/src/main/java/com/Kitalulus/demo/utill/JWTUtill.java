package com.Kitalulus.demo.utill;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JWTUtill {

    @Value("${jwt.token.issuer}")
    String tokenIssuer;

    @Value("${jwt.token.expiry:10}")
    Long tokenExpirySec;

    private final String SECRET = "mySecretKey";

    public String generateJwtToken(String username) {

        byte[] secretKey = TextCodec.BASE64.decode(SECRET);
        Instant issuedAt = Instant.now();
        Instant expireAt = issuedAt.plusSeconds(tokenExpirySec);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        return Jwts.builder().setIssuer(tokenIssuer).setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(Date.from(issuedAt)).setExpiration(Date.from(expireAt))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public static String getCurrentUser() {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        return user.getPrincipal().toString();
    }
}
