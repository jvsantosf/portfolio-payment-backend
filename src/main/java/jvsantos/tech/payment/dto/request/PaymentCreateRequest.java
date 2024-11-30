package jvsantos.tech.payment.dto.request;

import jvsantos.tech.payment.enums.PaymentStatus;
import java.math.BigDecimal;

public record PaymentCreateRequest(
        String name,
        PaymentStatus status,
        String message,
        String email,
        BigDecimal amount
) {
}
