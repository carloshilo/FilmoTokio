package com.tokioschool.filmotokio.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    @Schema(description = "Authentication Token")
    private final String token;

}
