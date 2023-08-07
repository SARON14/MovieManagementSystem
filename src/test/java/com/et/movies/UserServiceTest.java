package com.et.movies;

import com.et.movies.constants.ResponseCodes;
import com.et.movies.dto.request.SignUpRequest;
import com.et.movies.dto.request.UserRequestDto;
import com.et.movies.dto.response.JwtAuthenticationResponse;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.User;
import com.et.movies.repository.UserRepository;
import com.et.movies.service.impl.AuthenticationServiceImpl;
import com.et.movies.service.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationServiceImpl authenticationService;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("saron@gmail.com");
        signUpRequest.setPassword("password");

        when(userRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.SUCCESS_CODE, response.getToken());
        Assert.assertNotNull(response.getToken());
    }

    @Test
    public void testRegisterUser_DuplicateRegistration() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("saron@gmail.com");
        signUpRequest.setPassword("password");

        when(userRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.of(new User()));

        // Act
        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.FAILURE_CODE,null);
        Assert.assertEquals("Cannot proceed! Phone number 1234567890 already registered.", null);
    }
}