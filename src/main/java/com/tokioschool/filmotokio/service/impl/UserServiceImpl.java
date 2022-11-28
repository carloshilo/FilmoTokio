package com.tokioschool.filmotokio.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.filmotokio.dominio.dto.User;
import com.tokioschool.filmotokio.exception.UserNotFoundException;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void logged(User user) {
		LocalDateTime now = LocalDateTime.now();
		User logged = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
		logged.setLastLogin(now);
	    userRepository.save(logged);
	}

	@Override
	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> getById(long id) {
		return userRepository.findById(id);
	}
}
