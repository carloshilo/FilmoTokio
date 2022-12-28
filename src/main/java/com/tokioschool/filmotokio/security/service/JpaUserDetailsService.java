package com.tokioschool.filmotokio.security.service;

import com.tokioschool.filmotokio.exception.UserNotFoundException;
import com.tokioschool.filmotokio.service.UserService;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

  private final @NonNull UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.tokioschool.filmotokio.domain.User user;
    try {
      log.info("Fetching UserDetails for {}", username);
      user = userService.getByUsernameOrThrow(username);

    } catch (UserNotFoundException e) {
      log.error("User {} not found", username, e);
      throw new UsernameNotFoundException(e.getMessage(), e);
    }

    if (Objects.isNull(user.getRole())) {
      log.error("Login error: User {} doesn't have permission", username);
      throw new UsernameNotFoundException(
          String.format("Login error: User %s doesn't have permission", username));
    }

    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority(user.getRole().getName()));

    return User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .disabled(false)
        .credentialsExpired(false)
        .build();
  }

}
