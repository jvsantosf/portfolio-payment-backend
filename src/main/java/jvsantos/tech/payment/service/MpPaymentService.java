package jvsantos.tech.payment.service;

import jvsantos.tech.payment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

@Service
public class MpPaymentService {

    @Value("${mercado.pago.webhook.secret}")
    private String SECRET_KEY;

    public String isSecure(@RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) {
        String xSignature = headers.get("x-signature");
        String xRequestId = headers.get("x-request-id");

        if (xSignature == null || xRequestId == null) {
            return "Headers obrigatórios não encontrados";
        }

        String dataID = queryParams.get("data.id");

        if (dataID == null) {
            return "Parametro 'data.id' não encontrado";
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
            return "Assinatura inválida. 'ts' ou 'v1' ausente";
        }

        String manifest = String.format("id:%s;request-id:%s;ts:%s;", dataID, xRequestId, ts);

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(manifest.getBytes());
            String calculatedHash = Utils.bytesToHex(hmacBytes);

            if (calculatedHash.equals(hash)) {
                return "HMAC verification passed";
            } else {
                return "HMAC verification failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao validar HMAC: " + e.getMessage();
        }
    }

}
