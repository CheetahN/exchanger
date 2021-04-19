package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "original", columnDefinition = "enum('USD', 'RUR', 'EUR')", nullable = false)
    private Currency original;

    @Enumerated(EnumType.STRING)
    @Column(name = "target", columnDefinition = "enum('USD', 'RUR', 'EUR')", nullable = false)
    private Currency target;

    @Column(nullable = false)
    private Double rate;

    @Column(nullable = false)
    private LocalDateTime date;

    public Rate(Currency original, Currency target) {
        this.original = original;
        this.target = target;
    }
}
