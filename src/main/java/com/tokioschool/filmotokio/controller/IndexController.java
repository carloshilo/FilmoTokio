package com.tokioschool.filmotokio.controller;

import static com.tokioschool.filmotokio.utils.Constants.ADMIN_ROLE;

import com.tokioschool.filmotokio.domain.dto.CreateUserDTO;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.RoleService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("")
@AllArgsConstructor
public class IndexController {

  private final @NonNull RoleService roleService;
  private final @NonNull FilmService filmService;

  @GetMapping({"", "/index"})
  public String index(Model model, Authentication authentication) {
    model.addAttribute("titulo", "tokiofilm");
    model.addAttribute("welcome", "Bienvenidos a FilmTokio");
    model.addAttribute("films", filmService.getAll());
    return "index";
  }

  @GetMapping("/login")
  public String login(Model model) {
    return "login";
  }

  @GetMapping("/logout")
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(auth)) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/index";
  }

  @GetMapping("/signup")
  public String registerUser(Model model) {
    model.addAttribute("model", new CreateUserDTO());
    var authorization = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(authorization) && authorization.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(ADMIN_ROLE::equalsIgnoreCase)) {
      model.addAttribute("roles", roleService.findAll());
    }
    return "signup";
  }
}
