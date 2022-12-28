package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.service.UserService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Slf4j
@Component
@AllArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final @NonNull UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    var flashMapManager = new SessionFlashMapManager();
    var flashMap = new FlashMap();

    if (Objects.nonNull(authentication)) {
      log.info("User {} successfully logged in", authentication.getName());
      var user = userService.getByUsernameOrThrow(authentication.getName());
      userService.logged(user);
    }

    flashMap.put("success", "Hello " + authentication.getName() + ", you successfully logged in!");

    flashMapManager.saveOutputFlashMap(flashMap, request, response);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}
