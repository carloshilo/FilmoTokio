package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.security.jwt.JwtEntryPoint;
import com.tokioschool.filmotokio.security.jwt.JwtTokenFilter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

  private final @NonNull JwtEntryPoint jwtEntryPoint;
  private final @NonNull JwtTokenFilter jwtTokenFilter;

  @Bean
  public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder,
      UserDetailsService userDetailService)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder)
        .and()
        .build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/signup").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().antMatchers(
        "/resources/**",
        "/static/**",
        "/templates/**",
        "/images/**",
        "/styles/**",
        "/css/**",
        "/js/**",
        "/fonts/**",
        "/webjars/**",
        "/api/auth"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
