package Reward_Management_App.unit.controller;

import Reward_Management_App.user.controller.AuthController;
import Reward_Management_App.user.dto.data.UserData;
import Reward_Management_App.user.dto.request.LoginRequestDto;
import Reward_Management_App.user.dto.response.LoginResponseDto;
import Reward_Management_App.user.service.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserAuthService userAuthService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("testuser");
        loginRequestDto.setPassword("password");

        UserData userToken = new UserData();
        userToken.setJwtToken("Dummy-token");

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setData(userToken);

        when(userAuthService.userLogin(loginRequestDto)).thenReturn(loginResponseDto);

        ResponseEntity<?> response = authController.loginUser(loginRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponseDto, response.getBody());

        verify(userAuthService, times(1)).userLogin(loginRequestDto);
    }
}
