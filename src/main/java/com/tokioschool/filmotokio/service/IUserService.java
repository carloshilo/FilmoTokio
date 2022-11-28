package com.tokioschool.filmotokio.service;

import java.util.List;

import com.tokioschool.filmotokio.dominio.dto.User;

public interface IUserService {
	public List<User> findAll();
	
}
