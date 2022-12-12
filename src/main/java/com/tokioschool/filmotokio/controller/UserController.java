package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.service.RoleService;
import com.tokioschool.filmotokio.service.UserService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final @NonNull RoleService roleService;

  private final @NonNull UserService userService;

  @GetMapping("/login")
  public String login(Model model) {
    return "login";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("model", new CreateUserDTO());
    model.addAttribute("roles", roleService.findAll());
    return "signup";
  }

  @PostMapping (value="/signup")
  public String signup(@ModelAttribute @Valid CreateUserDTO createUserDTO, BindingResult result, Model model,
      SessionStatus status) {
    if (result.hasErrors()) {
      return "signup";
    }
    userService.create(createUserDTO);
    status.setComplete();
    return "redirect:/index";
  }
}
