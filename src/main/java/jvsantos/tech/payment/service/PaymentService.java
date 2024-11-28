package jvsantos.tech.payment.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentMethodRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import jvsantos.tech.payment.dto.PaymentCreateResponse;
import jvsantos.tech.payment.entity.Payer;
import jvsantos.tech.payment.exception.PaymentAlreadyCreatedException;
import jvsantos.tech.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository repository;

    @Value("${mercado.pago.access.token}")
    private String ACCESS_TOKEN;

    public PaymentCreateResponse create(Payer payment) throws PaymentAlreadyCreatedException {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
        PaymentClient client = new PaymentClient();
        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(payment.getValue())
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(payment.getEmail())
                                .build()
                )
                .build();

        try {
            Payment createdPayment = client.create(paymentCreateRequest);

            repository.save(payment);

            return PaymentCreateResponse.builder()
                    .id(createdPayment.getId())
                    .amount(createdPayment.getTransactionDetails().getTotalPaidAmount())
                    .status(createdPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                    .firstName(payment.getFirstName())
                    .message(payment.getMessage())
                    .build();

        } catch (MPException | MPApiException e) {
            LoggerFactory.getLogger(PaymentService.class).error(e.getMessage());
        }

        return null;
    }

}
