package com.tokioschool.filmotokio.controller;

import static com.tokioschool.filmotokio.utils.Constants.ADMIN_ROLE;

import com.tokioschool.filmotokio.dominio.dto.CreateUserDTO;
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

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final @NonNull RoleService roleService;

  @GetMapping("/login")
  public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("login");
    return modelAndView;
  }

}
