package jvsantos.tech.payment.dto.request;

import java.math.BigDecimal;

public record PaymentCreateRequest(
        String name,
        String message,
        String email,
        BigDecimal amount
) {
}
