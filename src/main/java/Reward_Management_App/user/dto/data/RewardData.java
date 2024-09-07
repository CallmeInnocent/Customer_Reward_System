package Reward_Management_App.user.dto.data;

import Reward_Management_App.user.entity.TransactionHistory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardData {

    private String customer_Id;
    private BigDecimal cashback;
    private BigDecimal current_Balance;
    private List<TransactionHistory> data;

}
