package com.tokioschool.filmotokio.controller;

import static com.tokioschool.filmotokio.utils.Constants.ADMIN_ROLE;

import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.RoleService;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("")
public class IndexController {

  private final @NonNull RoleService roleService;
  private final @NonNull FilmService filmService;

  @GetMapping({"", "/index"})
  public ModelAndView index(ModelAndView modelAndView) {

    modelAndView.addObject("titulo", "tokiofilm");
    modelAndView.addObject("welcome", "Bienvenidos a FilmTokio");
    modelAndView.addObject("films", filmService.getAll());
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping("/login")
  public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @GetMapping("/signup")
  public ModelAndView registerUser(ModelAndView modelAndView) {
    modelAndView.addObject("model", new CreateUserDTO());
    var authorization = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(authorization) && authorization.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(ADMIN_ROLE::equalsIgnoreCase)) {
      modelAndView.addObject("roles", roleService.findAll());
    }
    modelAndView.setViewName("signup");
    return modelAndView;
  }
}
