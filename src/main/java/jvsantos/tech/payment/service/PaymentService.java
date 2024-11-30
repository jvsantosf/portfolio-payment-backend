package jvsantos.tech.payment.service;

import jvsantos.tech.payment.dto.PaymentCreateResponse;
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

    public PaymentCreateResponse create(Payment payment) throws MpPaymentInvalidException {
        final var mpPayment = mpPaymentService.create(payment);

        if (mpPayment == null) {
            throw new MpPaymentInvalidException(payment.getId());
        }

        payment.setId(mpPayment.getId());
        payment.setStatus(PaymentStatus.CREATED);

        repository.save(payment);

        return PaymentCreateResponse.builder()
                .id(mpPayment.getId())
                .status(PaymentStatus.fromStatus(mpPayment.getStatus()))
                .firstName(payment.getFirstName())
                .qrCodeBase64(mpPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                .build();
    }

    public void updatePaymentStatus(long id, PaymentStatus status) throws MpPaymentInvalidException {
        Optional<Payment> optPayment = repository.findById(id);

        if (optPayment.isEmpty()) {
            throw new MpPaymentInvalidException(id);
        }

        final var payment = optPayment.get();
        payment.setStatus(status);

        repository.save(payment);

        log.info("Atualizando status pagamento de ID {} para {}", id, status);
    }

    public PaymentCreateResponse findPaymentById(long id) throws MpPaymentInvalidException {
        return repository.findById(id)
                .map(PaymentCreateResponse::new)
                .orElseThrow(() -> new MpPaymentInvalidException(id));
    }
}
