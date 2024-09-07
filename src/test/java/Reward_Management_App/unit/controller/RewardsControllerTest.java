package Reward_Management_App.unit.controller;

import Reward_Management_App.user.controller.RewardsController;
import Reward_Management_App.user.dto.data.RewardData;
import Reward_Management_App.user.dto.response.RewardBalanceResponseDto;
import Reward_Management_App.user.service.UserRewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RewardsControllerTest {

    @Mock
    private UserRewardService userService;

    @InjectMocks
    private RewardsController rewardsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRewardsBalance() {
        String customerId = "12345";
        RewardBalanceResponseDto rewardResponse = new RewardBalanceResponseDto();
        RewardData rewardData = new RewardData();
        rewardData.setCurrent_Balance(BigDecimal.valueOf(100));
        rewardResponse.setData(rewardData);
        when(userService.getRewardsBalance(customerId)).thenReturn(rewardResponse);

        ResponseEntity<?> response = rewardsController.getRewardsBalance(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rewardResponse, response.getBody());

        verify(userService, times(1)).getRewardsBalance(customerId);
    }

    @Test
    void testGetTransactionHistory() {

        String customerId = "12345";

        RewardBalanceResponseDto transactionHistoryResponse = new RewardBalanceResponseDto();
        RewardData rewardData = new RewardData();
        rewardData.setCurrent_Balance(BigDecimal.valueOf(50));
        transactionHistoryResponse.setData(rewardData);
        when(userService.getTransactionHistory(customerId)).thenReturn(transactionHistoryResponse);

        ResponseEntity<?> response = rewardsController.getTransactionHistory(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionHistoryResponse, response.getBody());

        verify(userService, times(1)).getTransactionHistory(customerId);
    }
}
