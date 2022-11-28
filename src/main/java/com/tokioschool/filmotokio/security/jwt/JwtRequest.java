package com.tokioschool.filmotokio.security.jwt;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = -4973204140021897181L;

	private String username;
	private String password;

}
