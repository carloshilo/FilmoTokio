package com.tokioschool.filmotokio.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tokioschool.filmotokio.domain.Role;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO extends PasswordDTO {

  @Serial
  private static final long serialVersionUID = -5659535844794150168L;

  private String username;
  private String name;
  private String surname;
  private String email;
  private Role role;

  @DateTimeFormat(style = "yyyy-MM-dd", pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;

}
