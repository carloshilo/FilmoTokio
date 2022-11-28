package com.tokioschool.filmotokio.service;

import java.util.List;
import java.util.Optional;

import com.tokioschool.filmotokio.dominio.dto.User;

public interface UserService {
	List<User> findAll();
	
	void logged(User user);
	
	Optional<User> getByUsername(String username);
	
	Optional<User> getById(long id);
	
}
