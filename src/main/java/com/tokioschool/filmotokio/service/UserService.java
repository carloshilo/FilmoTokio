package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import java.util.List;
import java.util.Optional;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.exception.UnauthorizedException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	List<User> findAll();
	
	void logged(User user);
	
	Optional<User> getByUsername(String username);

	User getByUsernameOrThrow(String username);
	
	Optional<User> getById(long id);

	User create(CreateUserDTO userDTO);

	void saveImage(User user, MultipartFile file);

	void saveImage(String username, MultipartFile file);

	User updateUser(String oldUsername, User user);

	void deleteUser(String username);

	void changePassword(String username, String oldPassword, String newPassword) throws UnauthorizedException;
}
