package jvsantos.tech.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class PaymentAlreadyCreatedException extends Exception {

    public PaymentAlreadyCreatedException(long paymentId) {
        super("O pagamento de ID #" + paymentId + " já existe no banco de dados.");
    }
}
