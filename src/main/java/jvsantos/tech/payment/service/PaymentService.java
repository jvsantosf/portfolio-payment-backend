package jvsantos.tech.payment.service;

import jvsantos.tech.payment.dto.request.PaymentCreateRequest;
import jvsantos.tech.payment.dto.response.PaymentCreateResponse;
import jvsantos.tech.payment.entity.Payment;
import jvsantos.tech.payment.enums.PaymentStatus;
import jvsantos.tech.payment.exception.MpPaymentInvalidException;
import jvsantos.tech.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final MpPaymentService mpPaymentService;

    public PaymentCreateResponse create(PaymentCreateRequest request) throws MpPaymentInvalidException {
        final var mpPayment = mpPaymentService.create(request);

        if (mpPayment == null) {
            throw new MpPaymentInvalidException();
        }

        repository.save(Payment.builder()
                        .withId(mpPayment.getId())
                        .withStatus(request.status())
                        .withFirstName(request.name())
                        .withEmail(request.email())
                        .withMessage(request.message())
                        .withLastName("Teste")
                        .withValue(request.amount())
                .build());

        return PaymentCreateResponse.builder()
                .id(mpPayment.getId())
                .name(request.name())
                .message(request.message())
                .email(request.email())
                .status(request.status())
                .amount(request.amount())
                .qrCodeBase64(mpPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                .build();
    }

    public void updatePaymentStatus(long id, PaymentStatus status) throws MpPaymentInvalidException {
        Optional<Payment> optPayment = repository.findById(id);

        if (optPayment.isEmpty()) {
            throw new MpPaymentInvalidException();
        }

        final var payment = optPayment.get();
        payment.setStatus(status);

        repository.save(payment);

        log.info("Atualizando status pagamento de ID {} para {}", id, status);
    }

    public PaymentCreateResponse findPaymentById(long id) throws MpPaymentInvalidException {
        return repository.findById(id)
                .map(PaymentCreateResponse::new)
                .orElseThrow(MpPaymentInvalidException::new);
    }
}
