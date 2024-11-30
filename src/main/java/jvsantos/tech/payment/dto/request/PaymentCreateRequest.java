package jvsantos.tech.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jvsantos.tech.payment.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentCreateRequest(
        String name,
        PaymentStatus status,
        String message,
        String email,
       @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal amount
) {
}
