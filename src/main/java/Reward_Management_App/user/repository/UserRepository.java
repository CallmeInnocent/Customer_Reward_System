package Reward_Management_App.user.repository;

import Reward_Management_App.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String customerId);
    Optional<User> findByEmail(String username);
}
