package jvsantos.tech.payment.controller;


import jvsantos.tech.payment.service.MpPaymentService;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/webhook")
public class MpPaymentController {

    private MpPaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> notification(@RequestBody String response, @RequestHeader Map<String, String> headers, @RequestParam Map<String, String> queryParams) {
        System.out.println(response);
        return ResponseEntity.ok(service.isSecure(headers, queryParams));
    }

}
