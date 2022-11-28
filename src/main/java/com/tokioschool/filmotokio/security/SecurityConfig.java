package com.tokioschool.filmotokio.security;

import static com.tokioschool.filmotokio.utils.Constants.ADMIN_ROLE;
import static com.tokioschool.filmotokio.utils.Constants.LOGIN_FAILURE;
import static com.tokioschool.filmotokio.utils.Constants.LOGIN_SUCCESS_URL;
import static com.tokioschool.filmotokio.utils.Constants.LOGIN_URL;
import static com.tokioschool.filmotokio.utils.Constants.LOGOUT_SUCCESS_URL;
import static com.tokioschool.filmotokio.utils.Constants.LOGOUT_URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tokioschool.filmotokio.security.jwt.JwtAuthenticationEntryPoint;
import com.tokioschool.filmotokio.security.jwt.JwtRequestFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  private final UserDetailsServiceImpl userDetailsService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
        .disable()
      .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/admin/**").hasAnyAuthority(ADMIN_ROLE)
        .anyRequest().authenticated()
    .and()
      .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
    .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    .and()
      .formLogin()
        .loginPage(LOGIN_URL)
        .loginProcessingUrl(LOGIN_URL)
        .successForwardUrl(LOGIN_SUCCESS_URL)
        .failureUrl(LOGIN_FAILURE)
    .and()
      .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
        .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
    .and()
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
      .headers().frameOptions().disable();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
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
    return new BCryptPasswordEncoder(-1);
  }
}
