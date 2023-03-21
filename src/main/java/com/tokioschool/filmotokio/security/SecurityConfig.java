package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.security.jwt.JwtAuthenticationEntryPoint;
import com.tokioschool.filmotokio.security.jwt.JwtRequestFilter;
import com.tokioschool.filmotokio.security.service.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

  private final @NonNull LoginSuccessHandler successHandler;
  private final JpaUserDetailsService userDetailsService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

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
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/index", "/users/signup",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**", "/api/auth").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler(successHandler)
        .loginPage("/login").permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .permitAll()
        .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .accessDeniedPage("/error_403");

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
        "/webjars/**"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
