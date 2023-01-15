package test.payment.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.payment.service.dto.PaymentDTO;
import test.payment.service.service.PaymentService;

@RestController
@RequestMapping("/payment_service")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<Long> createPayment(@RequestBody PaymentDTO paymentRequest) {

        return ResponseEntity.ok(
                paymentService.createPayment(paymentRequest.getClientName(), paymentRequest.getAmount()));
    }

    @PostMapping("/status/set")
    public ResponseEntity<String> setupStatus(@RequestBody Long paymentId) {

        return ResponseEntity.ok(paymentService.statusSetup(paymentId).toString());
    }
    @PostMapping("/status/get")
    public ResponseEntity<String> getStatus(@RequestBody Long paymentId) {
        return ResponseEntity.ok(paymentService.getStatus(paymentId));
    }
}
