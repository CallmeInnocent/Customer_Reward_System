package Reward_Management_App.user.controller;

import Reward_Management_App.user.dto.response.RewardBalanceResponseDto;
import Reward_Management_App.user.service.UserRewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardsController {


    private final UserRewardService userService;

    public RewardsController(UserRewardService userService) {
        this.userService = userService;
    }


    @GetMapping("/balance/{customer_Id}")
    public ResponseEntity<?> getRewardsBalance(@PathVariable String customer_Id ) {

        RewardBalanceResponseDto rewardResponse= userService.getRewardsBalance(customer_Id);

        return ResponseEntity.ok(rewardResponse);

    }

    @GetMapping("/history/{customer_Id}")
    public ResponseEntity<?> getTransactionHistory(@PathVariable String customer_Id ) {

        RewardBalanceResponseDto rewardResponse= userService.getTransactionHistory(customer_Id);

        return ResponseEntity.ok(rewardResponse);

    }

}
