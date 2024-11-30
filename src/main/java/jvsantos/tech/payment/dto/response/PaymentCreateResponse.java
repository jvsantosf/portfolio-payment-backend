package jvsantos.tech.payment.dto.response;

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
    private String name;
    private String message;
    private String email;
    private PaymentStatus status;
    private BigDecimal amount;
    private String qrCodeBase64;

    public PaymentCreateResponse(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getValue();
        this.status = payment.getStatus();
        this.message = payment.getMessage();
        this.name = payment.getFirstName();
    }
}
