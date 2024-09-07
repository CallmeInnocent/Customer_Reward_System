package Reward_Management_App.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Reward_Management_App.user.dto.response.RewardBalanceResponseDto;
import Reward_Management_App.user.entity.TransactionHistory;
import Reward_Management_App.user.entity.User;
import Reward_Management_App.user.exception.UserNotFoundException;
import Reward_Management_App.user.repository.UserRepository;
import Reward_Management_App.user.service.UserRewardService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RewardServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRewardService rewardService;

    public RewardServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToGetRewardsBalance() {
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setFirstName("John");
        mockUser.setTotalCashback(BigDecimal.valueOf(100.0));
        mockUser.setCurrentBalance(BigDecimal.valueOf(500.0));

        when(userRepository.findById("123")).thenReturn(Optional.of(mockUser));

        RewardBalanceResponseDto response = rewardService.getRewardsBalance("123");

        assertNotNull(response);
        assertEquals("Success: Hi,John here are your available balances", response.getMessage());
        assertEquals(200, response.getStatus_Code());
        assertNotNull(response.getData());
        assertEquals("123", response.getData().getCustomer_Id());
        assertEquals(new BigDecimal("100.0"), response.getData().getCashback()); // Comparing with BigDecimal
        assertEquals(new BigDecimal("500.0"), response.getData().getCurrent_Balance());

        verify(userRepository, times(1)).findById("123");
    }

    @Test
    public void testGetTransactionHistory_Success() {
        // Mocking the TransactionHistory list with BigDecimal and LocalDateTime
        List<TransactionHistory> mockTransactionHistory = new ArrayList<>();
        mockTransactionHistory.add(new TransactionHistory(
                "TX123",
                new BigDecimal("100.0"),
                "Purchase",
                LocalDateTime.now(),
                null
        ));


        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setFirstName("John");
        mockUser.setTransactionHistory(mockTransactionHistory);

        when(userRepository.findById("123")).thenReturn(Optional.of(mockUser));

        RewardBalanceResponseDto response = rewardService.getTransactionHistory("123");

        assertNotNull(response);
        assertEquals("Success: Hi, John here are your available transaction balances", response.getMessage());
        assertEquals(HttpStatus.OK.value(), response.getStatus_Code());
        assertNotNull(response.getTransactionData());
        assertEquals(1, response.getTransactionData().size());
        assertEquals("TX123", response.getTransactionData().get(0).getId());
        assertEquals(new BigDecimal("100.0"), response.getTransactionData().get(0).getAmountEarned());
        assertEquals("Purchase", response.getTransactionData().get(0).getDescription());

        verify(userRepository, times(1)).findById("123");
    }

    @Test
    public void testGetTransactionHistory_UserNotFound() {

        when(userRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> rewardService.getTransactionHistory("123"));
        verify(userRepository, times(1)).findById("123");
    }
}
