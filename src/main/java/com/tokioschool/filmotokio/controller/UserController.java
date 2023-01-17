package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.dto.ChangePasswordDTO;
import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.domain.dto.EditUserDTO;
import com.tokioschool.filmotokio.exception.UnauthorizedException;
import com.tokioschool.filmotokio.service.RoleService;
import com.tokioschool.filmotokio.service.UserService;
import java.security.Principal;
import java.util.Objects;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
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
  public String login(Model model) {
    return "login";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("model", new CreateUserDTO());
    model.addAttribute("roles", roleService.findAll());
    return "signup";
  }

  @PostMapping(value = "/signup")
  public String signup(@ModelAttribute @Valid CreateUserDTO createUserDTO, BindingResult result,
      Model model,
      SessionStatus status) {

    if (result.hasErrors()) {
      model.addAttribute("model", createUserDTO);
      model.addAttribute("roles", roleService.findAll());
      return "signup";
    }
    userService.create(createUserDTO);
    status.setComplete();
    return "redirect:/index";
  }

  @GetMapping("/profile")
  public String profile(Model model, Principal principal) {
    if (Objects.nonNull(principal)) {
      var user = userService.getByUsernameOrThrow(principal.getName());
      model.addAttribute("user", user);
      return "/profile";
    } else {
      return "redirect:/index";
    }
  }

  @GetMapping("/profile/{username}/films")
  public String userFilms(ModelAndView modelAndView, @PathVariable("username") String username) {
    User user = userService.getByUsernameOrThrow(username);
    modelAndView.addObject("films", user.getFilms());
    return "searched-film";
  }

  @PostMapping("/image")
  public String userImage(@RequestParam("userImage") MultipartFile imageFile, Principal principal) {
    log.info("Saving User {} userImage", principal.getName());
    userService.saveImage(principal.getName(), imageFile);
    return "redirect:/profile/edit";
  }

  @GetMapping("/profile/edit")
  public String editProfile(Model model, Principal principal) {
    User user = userService.getByUsernameOrThrow(principal.getName());
    model.addAttribute("editUserDTO", new EditUserDTO(user));
    model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
    return "edit-profile";
  }

  @PostMapping("/edit")
  public String updateUser(@Valid @ModelAttribute("editUserDTO") EditUserDTO editUserDTO,
      BindingResult result,
      ModelAndView modelAndView,
      Principal principal) {
    if (result.hasErrors()) {
      modelAndView.addObject("changePasswordDTO", new ChangePasswordDTO());
      return "edit-profile";
    } else {
      String loggedInUsername = principal.getName();
      log.info("Updating {} to {}", loggedInUsername, editUserDTO);
      User updated = userService.updateUser(loggedInUsername, editUserDTO.map());
      modelAndView.addObject("user", updated);
      return "redirect:/profile/";
    }
  }

  @PostMapping("/change-password")
  public String changePassword(
      @Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePassword,
      BindingResult result,
      ModelAndView modelAndView,
      Authentication authentication) {
    if (result.hasErrors()) {
      modelAndView.addObject("editUserDTO", new EditUserDTO((User) authentication.getPrincipal()));
      modelAndView.addObject("error", true);
      return "edit-profile";
    }
    log.info("Changing Password for {}", authentication.getName());
    userService.changePassword(authentication.getName(), changePassword.getOldPassword(),
        changePassword.getPassword());
    return "redirect:/profile/edit";
  }

  @GetMapping("/profile/delete")
  public String delete(ModelAndView modelAndView) {
    return "delete-user";
  }

  @GetMapping("/delete")
  public String deleteUser(Principal principal) throws UnauthorizedException {
    log.info("Deleting User {}", principal.getName());
    userService.deleteUser(principal.getName());
    return "redirect:/logout";
  }
}
