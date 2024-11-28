package jvsantos.tech.payment.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhook")
public class MpPaymentController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void notification(@RequestBody String response) {
        System.out.println(response);
    }

}
