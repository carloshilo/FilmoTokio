package com.tokioschool.filmotokio.security.jwt;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.properties.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JwtProvider {

  public static final String BEARER = "Bearer ";

  private final @NonNull JwtProperties jwtProperties;

  public String generateToken(Authentication authentication) {
    var user = (User) authentication.getPrincipal();
    var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
    return Jwts.builder().setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtProperties.expiration() * 1000))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public String getUsername(String token) {
    var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
    var parser = Jwts.parserBuilder().setSigningKey(secretKey).build();

    return parser.parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    if (StringUtils.isBlank(token)) {
      return false;
    }
    try {
      var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
      var parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
      parser.parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Malformed token", e);
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported token", e);
    } catch (ExpiredJwtException e) {
      log.error("Expired token", e);
    } catch (IllegalArgumentException e) {
      log.error("Empty token", e);
    } catch (SignatureException e) {
      log.error("Signature fail", e);
    } catch (NullPointerException e) {
      log.error("Not have authorization", e);
    }
    return false;
  }

}
