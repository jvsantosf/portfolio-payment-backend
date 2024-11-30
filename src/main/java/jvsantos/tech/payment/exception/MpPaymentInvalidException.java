package jvsantos.tech.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MpPaymentInvalidException extends Exception {

    public MpPaymentInvalidException(long id) {
        super("O pagamento de id #" + id + " é inválido");
    }
}
