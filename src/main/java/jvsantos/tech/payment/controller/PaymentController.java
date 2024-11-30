package jvsantos.tech.payment.controller;

import jvsantos.tech.payment.dto.request.PaymentCreateRequest;
import jvsantos.tech.payment.dto.response.PaymentCreateResponse;
import jvsantos.tech.payment.entity.Payment;
import jvsantos.tech.payment.exception.MpPaymentInvalidException;
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
    public PaymentCreateResponse create(@RequestBody PaymentCreateRequest request) throws MpPaymentInvalidException {
        return service.create(request);
    }

    @GetMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentCreateResponse findById(@PathVariable("id") long id) throws MpPaymentInvalidException {
        return service.findPaymentById(id);
    }

}
