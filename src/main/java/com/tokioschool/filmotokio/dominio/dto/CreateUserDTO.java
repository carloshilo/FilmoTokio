package com.tokioschool.filmotokio.dominio.dto;

import com.tokioschool.filmotokio.dominio.Role;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO extends PasswordDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -5659535844794150168L;

  private String username;
  private String name;
  private String surname;
  private String email;
  private Set<Role> roles = new HashSet<>();

  private String birthDate;
  private String password;
  private String confirmPassword;

}
