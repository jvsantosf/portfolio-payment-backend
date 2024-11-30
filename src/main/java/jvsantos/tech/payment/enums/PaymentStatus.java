package jvsantos.tech.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    NORMAL("payment"),
    UPDATED("payment.updated"),
    CREATED("payment.created"),
    ACCEPTED("payment.accepted");

    private final String status;

    public static PaymentStatus fromStatus(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.status.equals(status)) {
                return paymentStatus;
            }
        }
        return null;
    }

}
