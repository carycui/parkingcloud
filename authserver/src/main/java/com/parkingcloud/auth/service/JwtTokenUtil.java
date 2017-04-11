package com.parkingcloud.auth.service;

import com.parkingcloud.auth.domain.AuthRequest;
import com.parkingcloud.auth.exception.BadTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by caryc on 2017/4/6.
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${spring.application.name}")
    private String issuer;

    private Claims claims;

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public String generateToken(UserDetails userDetails, AuthRequest authRequest) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        // We can add more customer claims here
        return Jwts.builder()
                .setId(issuer+authRequest.getClient()+ UUID.randomUUID())
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setAudience(authRequest.getDevice())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean parseToken(String token) throws BadTokenException {
        try {
            this.claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e){
        }
        return false;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String getId(){
        return claims.getId();
    }

    public String getIssuer(){
        return claims.getIssuer();
    }

    public String getSubject(){
        return claims.getSubject();
    }

    public String getAudience(){
        return claims.getAudience();
    }

    public Date getExpiration() {
        return claims.getExpiration();
    }

    public Date getNotBefore() {
        return claims.getNotBefore();
    }

    public Date getIssuedAt() {
        return claims.getIssuedAt();
    }

    public String getUsername() {
        return this.getSubject();
    }

    public  Boolean isExpired() {
        final Date expiration = getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validate(Date lastPasswordReset) {
        final Date created = getIssuedAt();
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isExpired();
    }

}