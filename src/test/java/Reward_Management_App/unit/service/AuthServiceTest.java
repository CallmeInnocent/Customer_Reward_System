package Reward_Management_App.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Reward_Management_App.securityConfig.JwtService;
import Reward_Management_App.user.dto.request.LoginRequestDto;
import Reward_Management_App.user.dto.response.LoginResponseDto;
import Reward_Management_App.user.entity.User;
import Reward_Management_App.user.exception.IncorrectPasswordException;
import Reward_Management_App.user.exception.UserNotFoundException;
import Reward_Management_App.user.repository.UserRepository;
import Reward_Management_App.user.service.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwt;
    @InjectMocks
    private UserAuthService loginService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserLogin_Success() {
        LoginRequestDto loginRequest = new LoginRequestDto("user@example.com", "password123");

        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUser); // Assuming UserDetails is User

        when(jwt.generateToken(any(UserDetails.class))).thenReturn("mockJwtToken");

        LoginResponseDto response = loginService.userLogin(loginRequest);

        assertNotNull(response);
        assertEquals("Login Successful! Welcome to Balanceè, where we Redefine your vehicle repairs", response.getMessage());
        assertEquals(HttpStatus.OK.value(), response.getStatus_Code());
        assertNotNull(response.getData());
        assertEquals("mockJwtToken", response.getData().getJwtToken());
        assertEquals("123", response.getData().getData().getCustomer_Id());
        assertEquals("John", response.getData().getData().getFirst_Name());
        assertEquals("Doe", response.getData().getData().getLast_Name());
        assertEquals("user@example.com", response.getData().getData().getEmail());

        verify(userRepository, times(1)).findByEmail("user@example.com");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt, times(1)).generateToken(any(UserDetails.class));
    }

    @Test
    public void testUserLogin_UserNotFound() {

        LoginRequestDto loginRequest = new LoginRequestDto("user@example.com", "password123");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> loginService.userLogin(loginRequest));

        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    public void testUserLogin_IncorrectPassword() {

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        LoginRequestDto loginRequest = new LoginRequestDto("user@example.com", "password123");

        assertThrows(IncorrectPasswordException.class, () -> loginService.userLogin(loginRequest));

        verify(userRepository, times(1)).findByEmail("user@example.com");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
    }
}
