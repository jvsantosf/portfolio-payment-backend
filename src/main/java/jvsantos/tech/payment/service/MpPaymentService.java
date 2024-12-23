package jvsantos.tech.payment.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import jvsantos.tech.payment.dto.response.MpPaymentUpdateResponse;
import jvsantos.tech.payment.enums.PaymentStatus;
import jvsantos.tech.payment.exception.MpPaymentInvalidException;
import jvsantos.tech.payment.repository.PaymentRepository;
import jvsantos.tech.payment.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MpPaymentService {

    @Value("${mercado.pago.webhook.secret}")
    private String SECRET_KEY;

    @Value("${mercado.pago.access.token}")
    private String ACCESS_TOKEN;

    @NonNull
    private PaymentRepository paymentRepository;

    public void updatePaymentStatus(MpPaymentUpdateResponse response) throws MpPaymentInvalidException {
        final var id = Long.parseLong(response.id());

        Optional<jvsantos.tech.payment.entity.Payment> optPayment = paymentRepository.findById(id);

        if (optPayment.isEmpty()) {
            throw new MpPaymentInvalidException();
        }

        final var payment = optPayment.get();
        payment.setStatus(PaymentStatus.fromStatus(response.action()));

        paymentRepository.save(payment);

        log.info("Atualizando status pagamento de ID {} para {}", response.id(), response.action());
    }

    public Payment create(jvsantos.tech.payment.dto.request.PaymentCreateRequest request) {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
        PaymentClient client = new PaymentClient();

        log.info("Objeto enviado: {}", request);

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.amount())
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(request.email())
                                .build()
                )
                .build();

        try {
            return client.create(paymentCreateRequest);
        } catch (MPException | MPApiException e) {
            log.error("Não foi possível criar o pagamento {}", e.getMessage());
        }

        return null;
    }

    public boolean isSecure(@RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) {
        String xSignature = headers.get("x-signature");
        String xRequestId = headers.get("x-request-id");

        if (xSignature == null || xRequestId == null) {
            return false;
        }

        String dataID = queryParams.get("data.id");

        if (dataID == null) {
            return false;
        }

        String[] parts = xSignature.split(",");
        String ts = null;
        String hash = null;

        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if ("ts".equals(key)) {
                    ts = value;
                } else if ("v1".equals(key)) {
                    hash = value;
                }
            }
        }

        if (ts == null || hash == null) {
            return false;
        }

        String manifest = String.format("id:%s;request-id:%s;ts:%s;", dataID, xRequestId, ts);

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(manifest.getBytes());
            String calculatedHash = Utils.bytesToHex(hmacBytes);

            return calculatedHash.equals(hash);
        } catch (Exception e) {
            return false;
        }
    }

}
