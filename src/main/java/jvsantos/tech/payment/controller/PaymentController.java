package jvsantos.tech.payment.controller;

import jvsantos.tech.payment.dto.PaymentCreateResponse;
import jvsantos.tech.payment.entity.Payer;
import jvsantos.tech.payment.exception.PaymentAlreadyCreatedException;
import jvsantos.tech.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentCreateResponse create(@RequestBody Payer request) throws PaymentAlreadyCreatedException {
        return service.create(request);
    }

}
