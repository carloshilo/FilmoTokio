package com.tokioschool.filmotokio.security.jwt;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
	
	

    @Serial
	private static final long serialVersionUID = -6663478338925398273L;
	private final String token;

}

