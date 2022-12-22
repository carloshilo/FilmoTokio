package com.tokioschool.filmotokio.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -4973204140021897181L;

	@NotNull
	private String username;

	@NotNull
	private String password;

}
