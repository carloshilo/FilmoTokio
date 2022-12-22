package com.tokioschool.filmotokio.domain.dto;

import com.tokioschool.filmotokio.domain.annotation.SamePassword;
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SamePassword
public class PasswordDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -7135428479421064959L;

  @NotBlank(message = "{field.mandatory}")
  @Size(min = 8, max = 20, message = "{field.password.length}")
  protected String password;
  @NotNull(message = "{field.password.match}")
  protected String confirmPassword;

}
