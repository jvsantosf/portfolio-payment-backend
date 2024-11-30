package jvsantos.tech.payment.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jvsantos.tech.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Data
@Entity
@Table(name = "payments_test")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private PaymentStatus status;

    @Column
    private String firstName;

    @Column
    @Nullable
    private String lastName;

    @Column
    private String email;

    @Column
    private String message;

    @Column
    private BigDecimal value;

}
