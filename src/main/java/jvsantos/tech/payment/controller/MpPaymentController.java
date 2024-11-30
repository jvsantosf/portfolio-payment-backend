package jvsantos.tech.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jvsantos.tech.payment.dto.response.MpPaymentUpdateResponse;
import jvsantos.tech.payment.exception.MpPaymentInvalidException;
import jvsantos.tech.payment.service.MpPaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/webhook")
public class MpPaymentController {

    private MpPaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> notification(@RequestBody String response, @RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) throws MpPaymentInvalidException {
        log.info("Response recebida: {}", response);

        if (!service.isSecure(headers, queryParams)) {
            log.info("O POST enviado para o webhook é desconhecido.");
            return ResponseEntity.badRequest().build();
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            service.updatePaymentStatus(mapper.readValue(response, MpPaymentUpdateResponse.class));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
