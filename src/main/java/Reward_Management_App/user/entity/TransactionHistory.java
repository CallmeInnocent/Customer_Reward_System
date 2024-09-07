package Reward_Management_App.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    private String id;

    @Column(name = "amount_earned")
    private BigDecimal amountEarned;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private User user;
}
