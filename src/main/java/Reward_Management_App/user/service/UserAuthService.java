package Reward_Management_App.user.service;

import Reward_Management_App.securityConfig.JwtService;
import Reward_Management_App.user.dto.data.UserData;
import Reward_Management_App.user.dto.data.UserDetail;
import Reward_Management_App.user.dto.request.LoginRequestDto;
import Reward_Management_App.user.dto.response.LoginResponseDto;
import Reward_Management_App.user.entity.User;
import Reward_Management_App.user.exception.IncorrectPasswordException;
import Reward_Management_App.user.exception.UserNotFoundException;
import Reward_Management_App.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwt;

    private final UserRepository userRepository;

    public UserAuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwt, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
        this.userRepository = userRepository;
    }

    public LoginResponseDto userLogin(LoginRequestDto loginRequest) {

            User foundUser = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("No Account found with Username " +loginRequest.getEmail()));

            if (!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
                throw new IncorrectPasswordException("The password is incorrect");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwt.generateToken(userDetails);

            UserDetail userDetail = new UserDetail();
            userDetail.setCustomer_Id(foundUser.getId());
            userDetail.setFirst_Name(foundUser.getFirstName());
            userDetail.setLast_Name(foundUser.getLastName());
            userDetail.setEmail(foundUser.getEmail());
            UserData userData = new UserData(jwtToken, userDetail);

            return new LoginResponseDto("Login Successful! Welcome to Balanceè, where we Redefine your vehicle repairs" , HttpStatus.OK.value(), userData);

    }
}
