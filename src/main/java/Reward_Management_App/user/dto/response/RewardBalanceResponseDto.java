package Reward_Management_App.user.dto.response;

import Reward_Management_App.user.dto.data.RewardData;
import Reward_Management_App.user.entity.TransactionHistory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardBalanceResponseDto {

    private String message;
    private int status_Code;
    private RewardData data;
    private List<TransactionHistory> transactionData;
}
