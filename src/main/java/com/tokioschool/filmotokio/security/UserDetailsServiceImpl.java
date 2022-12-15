package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.exception.UserNotFoundException;
import com.tokioschool.filmotokio.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final @NonNull UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("Fetching UserDetails for {}", username);
            return userService.getByUsernameOrThrow(username);

        } catch (UserNotFoundException e) {
            log.error("User {} not found", username, e);
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

}
