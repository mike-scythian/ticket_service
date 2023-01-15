package test.payment.service.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.payment.service.model.Client;
import test.payment.service.model.Payment;
import test.payment.service.model.PaymentStatus;
import test.payment.service.repository.PaymentRepository;


@Service
@AllArgsConstructor
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Long createPayment(String clientName, Double amount){
        Payment newPayment = new Payment();
        newPayment.setClientName(clientName);
        newPayment.setStatus(PaymentStatus.NEW);
        newPayment.setAmount(amount);
        return paymentRepository.save(newPayment).getId();
    }
    public PaymentStatus statusSetup(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus(PaymentStatus.randomGenerator());
        return paymentRepository.save(payment).getStatus();
    }
    public String getStatus(Long paymentId){
        return paymentRepository.findById(paymentId).orElseThrow().getStatus().toString();
    }
    private Client stringToClient(String clientName){
        String[] name = clientName.split(" ");
        return new Client(name[0], name[1]);
    }
}
