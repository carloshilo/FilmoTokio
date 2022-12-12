package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.service.RoleService;
import com.tokioschool.filmotokio.service.UserService;
import java.util.Objects;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final @NonNull RoleService roleService;

  private final @NonNull UserService userService;

  @GetMapping("/login")
  public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @GetMapping("/register")
  public ModelAndView registerUser(ModelAndView modelAndView) {
    modelAndView.addObject("createUserDTO", new CreateUserDTO());
    modelAndView.addObject("roles", roleService.findAll());
    modelAndView.setViewName("signup");
    return modelAndView;
  }

  @PostMapping("/register")
  public String registerNewUser(@RequestParam("userImage") MultipartFile userImage,
      @ModelAttribute @Valid CreateUserDTO createUserDTO,
      BindingResult result) {
    if (result.hasErrors()) {
      return "signup";
    }
    User newUser = userService.create(createUserDTO);
    if (Objects.nonNull(userImage) && !userImage.isEmpty()) {
      userService.saveImage(newUser, userImage);
    }
    return "login";
  }
}
