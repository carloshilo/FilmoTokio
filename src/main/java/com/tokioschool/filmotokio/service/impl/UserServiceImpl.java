package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.exception.UnauthorizedException;
import com.tokioschool.filmotokio.exception.UserNotFoundException;
import com.tokioschool.filmotokio.exception.UsernameAlreadyExistsException;
import com.tokioschool.filmotokio.properties.FileDirectoryProperties;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final @NonNull FileService fileService;

  private final @NonNull UserRepository userRepository;

  private final @NonNull FileDirectoryProperties directoryProperties;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void logged(User user) {
    var logged = getByUsernameOrThrow(user.getUsername());
    logged.setLastLogin(LocalDateTime.now());
    userRepository.save(logged);
  }

  @Override
  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User getByUsernameOrThrow(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> {
          log.error(String.format("User %s not found", username));
          throw new UsernameNotFoundException(String.format("User %s not found", username));
        });
  }

  @Override
  public Optional<User> getById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public User create(CreateUserDTO userDTO) {
    if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
      throw new UsernameAlreadyExistsException("Username not available");
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    User user = User.builder()
        .name(userDTO.getName())
        .surname(userDTO.getSurname())
        .username(userDTO.getUsername())
        .password(encoder.encode(userDTO.getPassword()))
        .email(userDTO.getEmail())
        .birthdate(userDTO.getBirthDate())
        .role(userDTO.getRole())
        .build();

    return userRepository.save(user);
  }
}
