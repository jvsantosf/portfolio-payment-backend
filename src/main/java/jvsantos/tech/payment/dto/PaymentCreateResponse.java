package jvsantos.tech.payment.dto;

import jvsantos.tech.payment.entity.Payment;
import jvsantos.tech.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class PaymentCreateResponse {

    private long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private String qrCodeBase64;
    private String message;
    private String firstName;

    public PaymentCreateResponse(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getValue();
        this.status = payment.getStatus();
        this.message = payment.getMessage();
        this.firstName = payment.getFirstName();
    }
}
