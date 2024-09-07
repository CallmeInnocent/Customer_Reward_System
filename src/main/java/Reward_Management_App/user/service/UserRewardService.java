package Reward_Management_App.user.service;

import Reward_Management_App.user.dto.data.RewardData;
import Reward_Management_App.user.dto.response.RewardBalanceResponseDto;
import Reward_Management_App.user.entity.TransactionHistory;
import Reward_Management_App.user.entity.User;
import Reward_Management_App.user.exception.UserNotFoundException;
import Reward_Management_App.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRewardService {

    private final UserRepository userRepository;

    public UserRewardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RewardBalanceResponseDto getRewardsBalance (String customerId){

        User customer = userRepository.findById(customerId).stream()
                .filter(foundCustomer -> foundCustomer.getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with Id: " +customerId + " not found"));

        RewardData customerData = new RewardData();
        customerData.setCustomer_Id(customer.getId());
        customerData.setCashback(customer.getTotalCashback());
        customerData.setCurrent_Balance(customer.getCurrentBalance());

        RewardBalanceResponseDto rewardResponseDto = new RewardBalanceResponseDto();
        rewardResponseDto.setMessage("Success: Hi," +customer.getFirstName() + " here are your available balances");
        rewardResponseDto.setStatus_Code(HttpStatus.OK.value());
        rewardResponseDto.setData(customerData);

        return rewardResponseDto;

    }

    public RewardBalanceResponseDto getTransactionHistory(String customerId){

        User customer = userRepository.findById(customerId).stream()
                .filter(foundUser -> foundUser.getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with id: " +customerId + " not found"));

        List<TransactionHistory> customerTransaction = customer.getTransactionHistory();

        RewardBalanceResponseDto rewardResponseDto = new RewardBalanceResponseDto();
        rewardResponseDto.setMessage("Success: Hi, " +customer.getFirstName() + " here are your available transaction balances");
        rewardResponseDto.setStatus_Code(HttpStatus.OK.value());
        rewardResponseDto.setTransactionData(customerTransaction);

        return rewardResponseDto;

    }


}
