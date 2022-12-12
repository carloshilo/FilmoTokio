package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import java.util.List;
import java.util.Optional;

import com.tokioschool.filmotokio.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	List<User> findAll();
	
	void logged(User user);
	
	Optional<User> getByUsername(String username);

	User getByUsernameOrThrow(String username);
	
	Optional<User> getById(long id);

	User create(CreateUserDTO userDTO);

	void saveImage(User user, MultipartFile file);
}
