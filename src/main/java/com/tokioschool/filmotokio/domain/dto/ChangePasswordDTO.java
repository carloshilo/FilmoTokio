package com.tokioschool.filmotokio.domain.dto;

import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordDTO extends PasswordDTO {

    @Serial
    private static final long serialVersionUID = 8382149290647308834L;

    private String oldPassword;
}
