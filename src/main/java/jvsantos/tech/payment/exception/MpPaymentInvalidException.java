package jvsantos.tech.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MpPaymentInvalidException extends Exception {

    public MpPaymentInvalidException() {
        super("Um pagamento prestes a ser criado foi tido como inválido");
    }
}
