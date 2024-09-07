package Reward_Management_App.user.dto.response;

import Reward_Management_App.user.dto.data.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String message;
    private int status_Code;
    private UserData data;

}
