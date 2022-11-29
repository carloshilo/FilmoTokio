package com.tokioschool.filmotokio.security.jwt;

import static com.tokioschool.filmotokio.security.jwt.JwtTokenUtil.BEARER;

import java.io.IOException;

import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		var token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (Objects.nonNull(token)) {
			if (StringUtils.startsWith(token, BEARER)) {
				log.info("Authenticating JWT authentication request");
				try {
					String username = jwtTokenUtil.getUsername(token);
					if (!username.isEmpty() && null == SecurityContextHolder.getContext().getAuthentication()) {
						log.info("Authenticating User {} JWT auth request", username);
						UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
						if (jwtTokenUtil.validateToken(token, userDetails)) {
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							usernamePasswordAuthenticationToken
									.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						}
					}
				} catch (IllegalArgumentException e) {
					log.error("Unable to fetch JWT Token");
				} catch (ExpiredJwtException e) {
					log.error("JWT Token is expired");
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				log.warn("JWT Token does not begin with Bearer String");
			}
		}
		chain.doFilter(request, response);
	}

}
