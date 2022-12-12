package com.tokioschool.filmotokio.security.jwt;

import static com.tokioschool.filmotokio.security.jwt.JwtProvider.BEARER;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.security.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final @NonNull JwtProvider jwtProvider;

  private final @NonNull UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      var token = getToken(request);
      if (jwtProvider.validateToken(token)) {
        var username = jwtProvider.getUsername(token);
        var user = (User) userDetailsService.loadUserByUsername(username);
        var authorization = new UsernamePasswordAuthenticationToken(user,
            null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authorization);

      }
    } catch (Exception e) {
      logger.error("Fail en el método doFilter " + e.getMessage());
    }
    filterChain.doFilter(request, response);
  }


  //Obtenemos el token sin Bearer + el espacio
  private String getToken(HttpServletRequest request) {
    var header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.startsWith(header, BEARER)) {
      return StringUtils.replace(header, BEARER, StringUtils.EMPTY);
    }
    return null;

  }

}
