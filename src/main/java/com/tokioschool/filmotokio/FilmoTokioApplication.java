package com.tokioschool.filmotokio;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.repository.RoleRepository;
import com.tokioschool.filmotokio.repository.UserRepository;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@AllArgsConstructor
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class FilmoTokioApplication {

  private final @NonNull RoleRepository roleRepository;
  private final UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(FilmoTokioApplication.class, args);
  }

  @PostConstruct
  void sendDatabase() {
    roleRepository.saveAll(List.of(
        Role.builder().id(1).name("ADMIN").build(),
        Role.builder().id(2).name("USER").build()));

    Calendar calendar = Calendar.getInstance();
    calendar.set(1990, Calendar.JANUARY, 1);

    userRepository.save(User.builder()
        .id(1)
        .role(Role.builder().id(1).name("ADMIN").build())
        .username("admin")
        .name("Admin")
        .surname("Super")
        .email("admin@gmail.com")
        .password("admin")
        .birthdate(calendar.getTime())
        .build());
  }
}
