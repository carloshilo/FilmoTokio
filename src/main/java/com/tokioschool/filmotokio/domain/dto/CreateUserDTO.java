package com.tokioschool.filmotokio.domain.dto;

import com.tokioschool.filmotokio.domain.Role;
import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  private String birthDate;
  private String password;
  private String confirmPassword;

}
