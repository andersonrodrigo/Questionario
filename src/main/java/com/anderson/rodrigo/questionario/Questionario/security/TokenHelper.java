package com.anderson.rodrigo.questionario.Questionario.security;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenHelper {
	private static long EXPIRES_IN = 720 * 60; //15 min
	private static String SECRET="5Uda*=ch=?uNuStAsT75e7?EsTA=?4HE";
	private static String TOKEN_PREFIX = "Bearer";
	private static String KEY_JWT = "KEY_JWT";
	private static String AUTH_HEADER = "Authorization";
	private static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	
	public String generateToken(final String username, final Map<String, Object> user) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .claim("user", user)
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }
    
    public String getToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTH_HEADER);
        final String tokenParam = request.getParameter(KEY_JWT);
        if ( authHeader != null && authHeader.startsWith(TOKEN_PREFIX+" ")) {
            return authHeader.substring(7);
        }else if ( tokenParam != null && !tokenParam.equals("")) {
            return tokenParam;
        } 
        return null;
    }
    
    public Claims getClaims(final String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final Exception e) {
            claims = null;
        }
        return claims;
    }
    
    public Claims getClaims(final HttpServletRequest request) {
    	return this.getClaims(this.getToken(request));
    }
    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + EXPIRES_IN * 1000);
    }
}