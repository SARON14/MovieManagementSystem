package com.et.movies.service;

import com.et.movies.dto.request.SignUpRequest;
import com.et.movies.dto.request.SigninRequest;
import com.et.movies.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
