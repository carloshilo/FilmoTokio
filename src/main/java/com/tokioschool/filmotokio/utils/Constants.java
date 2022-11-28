package com.tokioschool.filmotokio.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String LOGIN_FAILURE = "/users/login?error=true";
    public static final String LOGIN_URL = "/users/login";
    public static final String LOGIN_SUCCESS_URL = "/profile?message=login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGOUT_SUCCESS_URL = "/?message=logout";

}