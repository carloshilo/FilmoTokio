package com.tokioschool.filmotokio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.filmotokio.dominio.dto.User;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
