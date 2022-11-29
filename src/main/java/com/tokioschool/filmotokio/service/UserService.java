package com.tokioschool.filmotokio.service;

import java.util.List;
import java.util.Optional;

import com.tokioschool.filmotokio.dominio.User;

public interface UserService {
	List<User> findAll();
	
	void logged(User user);
	
	Optional<User> getByUsername(String username);

	User getByUsernameOrThrow(String username);
	
	Optional<User> getById(long id);
	
}
