package test.payment.service.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.payment.service.model.Client;
import test.payment.service.model.Payment;
import test.payment.service.model.PaymentStatus;
import test.payment.service.repository.PaymentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Long createPayment(String clientName, Double amount){
        Payment newPayment = new Payment();
        newPayment.setClientName(clientName);
        newPayment.setAmount(amount);
        return paymentRepository.save(newPayment).getId();
    }
    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }
    public PaymentStatus statusSetup(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus(PaymentStatus.randomGenerator());

        return paymentRepository.save(payment).getStatus();
    }
    private Client stringToClient(String clientName){
        String[] name = clientName.split(" ");
        return new Client(name[0], name[1]);
    }
}
