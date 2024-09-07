package Reward_Management_App.user.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedUserException extends RuntimeException{
    public UnAuthorizedUserException(String message) {
        super(message);
    }
}