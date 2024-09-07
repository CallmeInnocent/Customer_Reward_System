package Reward_Management_App.user.dto.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    private String customer_Id;
    private String first_Name;
    private String last_Name;
    private String email;
}
