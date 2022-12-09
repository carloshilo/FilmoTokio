package com.tokioschool.filmotokio.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PasswordDTO {
    @NotBlank(message = "{field.mandatory}")
    @Size(min= 8, message = "{field.password.length}")
    protected String password;
    @NotNull(message = "{field.password.match}")
    protected String confirmPassword;

    public void setPassword(String password) {
        this.password = password;
        confirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        confirmPassword();
    }

    private void confirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        }
        else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }

}
