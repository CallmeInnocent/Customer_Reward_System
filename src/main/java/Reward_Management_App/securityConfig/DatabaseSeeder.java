package Reward_Management_App.securityConfig;

import Reward_Management_App.user.entity.TransactionHistory;
import Reward_Management_App.user.entity.User;
import Reward_Management_App.user.enums.Role;
import Reward_Management_App.user.repository.TransactionHistoryRepository;
import Reward_Management_App.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (isDatabaseEmpty()) {
            seedDatabase();
        } else {
            System.out.println("Database is not empty, skipping seeding.");
        }
    }

    @Transactional(readOnly = true)
    public boolean isDatabaseEmpty() {
        return userRepository.count() == 0;
    }

    @Transactional
    public void seedDatabase() {

        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("johndoe@gmail.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setUserRole(Role.ROLE_USER);
        user1.setTotalCashback(BigDecimal.valueOf(7000));
        user1.setCurrentBalance(BigDecimal.valueOf(50000));
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("janesmith@gmail.com");
        user2.setPassword(passwordEncoder.encode("password2"));
        user2.setUserRole(Role.ROLE_USER);
        user2.setTotalCashback(BigDecimal.valueOf(11000));
        user2.setCurrentBalance(BigDecimal.valueOf(250000));
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.saveAll(Arrays.asList(user1, user2));

        TransactionHistory transaction1 = new TransactionHistory();
        transaction1.setId(UUID.randomUUID().toString());
        transaction1.setDescription("Car Repair");
        transaction1.setAmountEarned(BigDecimal.valueOf(500));
        transaction1.setCreatedAt(LocalDateTime.now());
        transaction1.setUser(user1);

        TransactionHistory transaction2 = new TransactionHistory();
        transaction2.setId(UUID.randomUUID().toString());
        transaction2.setDescription("Engine Cleaning");
        transaction2.setAmountEarned(BigDecimal.valueOf(750));
        transaction2.setCreatedAt(LocalDateTime.now());
        transaction2.setUser(user1);

        TransactionHistory transaction3 = new TransactionHistory();
        transaction3.setId(UUID.randomUUID().toString());
        transaction3.setDescription("Exhaust Repair");
        transaction3.setAmountEarned(BigDecimal.valueOf(1000));
        transaction3.setCreatedAt(LocalDateTime.now());
        transaction3.setUser(user2);

        TransactionHistory transaction4 = new TransactionHistory();
        transaction4.setId(UUID.randomUUID().toString());
        transaction4.setDescription("Bonnet Dent Repair");
        transaction4.setAmountEarned(BigDecimal.valueOf(570));
        transaction4.setCreatedAt(LocalDateTime.now());
        transaction4.setUser(user2);

        transactionHistoryRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3, transaction4));


    }
}
