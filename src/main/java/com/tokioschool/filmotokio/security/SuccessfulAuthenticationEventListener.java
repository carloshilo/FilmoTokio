package com.tokioschool.filmotokio.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.tokioschool.filmotokio.dominio.dto.User;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class SuccessfulAuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

  private final UserServiceImpl userService;


  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    User user = (User) event.getAuthentication().getPrincipal();
    log.info("User {} successfully logged in", user.getUsername());
    userService.userLoggedIn(user);
  }
}
