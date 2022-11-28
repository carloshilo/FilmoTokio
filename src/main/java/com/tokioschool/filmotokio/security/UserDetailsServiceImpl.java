package com.tokioschool.filmotokio.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserServiceImpl userService;

	public UserDetailsServiceImpl(UserServiceImpl userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			log.info("Fetching UserDetails for {}", username);
			return userService.getUser(username);

		} catch (UserNotFoundException e) {
			log.error("User {} not found", username, e);
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
	}

}
