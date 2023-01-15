package test.payment.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.payment.service.dto.PaymentDTO;
import test.payment.service.model.Client;
import test.payment.service.model.Payment;
import test.payment.service.model.PaymentStatus;
import test.payment.service.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment_service")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/")
    public ResponseEntity<List<Payment>> pong(){
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @PostMapping("/")
    public ResponseEntity<Long> createPayment(@RequestBody PaymentDTO paymentRequest) {

        return ResponseEntity.ok(
                paymentService.createPayment(paymentRequest.getClientName(), paymentRequest.getAmount()));
    }

    @PostMapping("/status")
    public ResponseEntity<String> setupStatus(@RequestBody Long paymentId) {

        return ResponseEntity.ok(paymentService.statusSetup(paymentId).toString());
    }
}
