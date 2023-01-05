package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.exception.UnauthorizedException;
import com.tokioschool.filmotokio.exception.UserNotFoundException;
import com.tokioschool.filmotokio.exception.UsernameAlreadyExistsException;
import com.tokioschool.filmotokio.repository.UserRepository;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.UserService;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final @NonNull FileService fileService;

    private final @NonNull UserRepository userRepository;

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

    @Override
    public void saveImage(User user, MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = String.format("%s.%s", UUID.randomUUID(), extension);

        user.setImage(fileName);
        userRepository.save(user);

        fileService.saveFile(file, fileName);
    }

    @Override
    public void saveImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        saveImage(user, file);

    }

    @Override
    @Transactional
    public User updateUser(String oldUsername, User user) {
        log.info("Updating User {}", oldUsername);
        // check if username is new
        if (!oldUsername.equals(user.getUsername())
                // and check if it's available
                && userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username unavailable.");
        }
        User toUpdate = userRepository.findByUsername(oldUsername)
                .orElseThrow(UserNotFoundException::new);
        toUpdate.update(user);
        updateSecurityContext(toUpdate.getUsername());
        return userRepository.save(toUpdate);
    }

    private void updateSecurityContext(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        userDetails.setUsername(username);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        log.info("Deleting User {}", username);
        User toDelete = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        userRepository.delete(toDelete);
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        log.info("Changing User's {} password", username);
        User toUpdate = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        // check user has entered correct old password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(-1);
        if (!encoder.matches(oldPassword, toUpdate.getPassword())) {
            throw new UnauthorizedException("Authorization Failure");
        }
        else {
            toUpdate.setPassword(encoder.encode(newPassword));
            userRepository.save(toUpdate);
        }
    }
}
