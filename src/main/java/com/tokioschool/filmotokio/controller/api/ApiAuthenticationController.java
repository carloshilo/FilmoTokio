package com.tokioschool.filmotokio.controller.api;

import com.tokioschool.filmotokio.security.jwt.JwtRequest;
import com.tokioschool.filmotokio.security.jwt.JwtResponse;
import com.tokioschool.filmotokio.security.jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "api", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Entry Point for Authentication with RESTful API")
@AllArgsConstructor
public class ApiAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    @Operation(summary = "Request Authentication Token for Provided Credentials")
    @ApiResponse(responseCode = "200", description = "Authentication Successful", content = @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping(path = "/auth")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws Exception {
        log.info("BEGIN login for username: {}", authRequest.getUsername());
        authenticate(authRequest.getUsername(), authRequest.getPassword());
        final var userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final var jwtToken = jwtTokenUtil.generateToken(userDetails);
        log.info("SUCCESS login:: token - {}", jwtToken);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    private void authenticate(String username, String password) throws Exception {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
    }
}
