package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.dominio.User;
import com.tokioschool.filmotokio.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SuccessfulAuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserServiceImpl userService;


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        log.info("User {} successfully logged in", user.getUsername());
        userService.logged(user);
    }
}
