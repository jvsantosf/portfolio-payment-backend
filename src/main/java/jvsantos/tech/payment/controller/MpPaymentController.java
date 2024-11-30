package jvsantos.tech.payment.controller;

import jvsantos.tech.payment.dto.MpPaymentUpdateResponse;
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
    public ResponseEntity<Object> notification(@RequestBody MpPaymentUpdateResponse response, @RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) throws MpPaymentInvalidException {
        if (!service.isSecure(headers, queryParams)) {
            log.info("O POST enviado para o webhook é desconhecido.");
            return ResponseEntity.badRequest().build();
        }

        service.updatePaymentStatus(response);
        return ResponseEntity.ok().build();
    }

}
