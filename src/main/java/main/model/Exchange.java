package main.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "exchange")
@Builder
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "original", columnDefinition = "enum('USD', 'RUR', 'EUR')", nullable = false)
    private Currency source;

    @Enumerated(EnumType.STRING)
    @Column(name = "target", columnDefinition = "enum('USD', 'RUR', 'EUR')", nullable = false)
    private Currency target;

    @Column(name = "amount_original", nullable = false)
    private Long amountSource;

    @Column(name = "amount_target", nullable = false)
    private Double amountTarget;

    @Column(nullable = false)
    private LocalDateTime date;
}
