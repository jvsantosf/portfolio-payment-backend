package jvsantos.tech.payment.dto;

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
    private String status;
    private String qrCodeBase64;
    private String message;
    private String firstName;

}
